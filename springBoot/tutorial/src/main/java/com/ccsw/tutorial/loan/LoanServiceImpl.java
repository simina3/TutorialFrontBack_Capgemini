package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.common.exception.DuplicateLoanException;
import com.ccsw.tutorial.common.exception.MaxLoanPerDayExceededException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanFilterDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    @Override
    public Loan get(Long id) {

        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
        LoanFilterDto filtersSelected = dto.getLoanFilterDto();
        Specification<Loan> spec = Specification.where(null);

        if (filtersSelected == null) {
            return this.loanRepository.findAll(null, dto.getPageable().getPageable());
        }

        LoanSpecification specGame = new LoanSpecification(
                new SearchCriteria("game.id", ":", filtersSelected.getGame()));
        LoanSpecification specClient = new LoanSpecification(
                new SearchCriteria("client.id", ":", filtersSelected.getClient()));
        LoanSpecification specStartDate = new LoanSpecification(
                new SearchCriteria("startDate", "<=", filtersSelected.getFilteredDate()));
        LoanSpecification specEndDate = new LoanSpecification(
                new SearchCriteria("endDate", ">=", filtersSelected.getFilteredDate()));

        spec = Specification.where(specGame).and(specClient).and(specStartDate).and(specEndDate);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, LoanDto dto) throws Exception {
        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            // loan = this.loanRepository.findById(id).orElse(null);
            loan = this.get(id);
            dto.setId(id);
        }

        BeanUtils.copyProperties(dto, loan, "id", "game", "client");

        // Obtenemos propiedades del dto al préstamo y
        Game game = gameService.get(dto.getGame().getId());
        Client client = clientService.get(dto.getClient().getId());

        // Asignamos juego y cliente al préstamo
        loan.setGame(game);
        loan.setClient(client);

        // Obtenemos las fechas de inicio y fin del préstamo
        LocalDate startDate = loan.getStartDate();
        LocalDate endDate = loan.getEndDate();

        // Validamos que la fecha de fin no sea anterior a la fecha de inicio
        if (endDate.isBefore(startDate)) {
            throw new Exception("La fecha de fin de préstamo no puede ser anterior a la fecha de comienzo de préstamo");
        }

        // Calculamos la diferencia de días entre la fecha de inicio y la fecha de fin
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        // Validamos que el periodo de préstamo máximo sea de 14 días
        if (daysBetween > 14) {
            throw new Exception("El período máximo de préstamo es de 14 días");
        }

        // Verificamos de préstamo duplicado para un juego en la misma fecha
        boolean loanExists = loanRepository.gameBorrowed(loan.getGame(), loan.getEndDate(), loan.getStartDate());

        if (loanExists) {
            throw new DuplicateLoanException("El juego ya está prestado en el rango de fechas seleccionado.");
        }

        // Verificamos que un cliente no tiene prestados más de dos juegos un mismo día
        boolean maxLoansPerDayExceeded = loanRepository.loanCountByClientAndDate(loan.getClient(), loan.getEndDate(), loan.getStartDate());

        if (maxLoansPerDayExceeded) {
            throw new MaxLoanPerDayExceededException(
                "El cliente ya tiene un juego prestado para la fecha seleccionada.");
        }

        // Guardamos el préstamo en el repositorio
        this.loanRepository.save(loan);
    }

    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }
        this.loanRepository.deleteById(id);
    }

    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }
}
