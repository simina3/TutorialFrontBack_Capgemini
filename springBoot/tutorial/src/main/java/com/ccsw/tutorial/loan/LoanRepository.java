package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable); // recupera un listado paginado de Préstamos
    
    @Override
    @EntityGraph(attributePaths = {"game", "client"})
    List<Loan> findAll(Specification<Loan> spec);
    
    @Query("SELECT COUNT(l) > 0 FROM Loan l WHERE l.game = :game AND ((l.startDate <= :endDate AND l.endDate >= :startDate) OR (l.startDate BETWEEN :startDate AND :endDate) OR (l.endDate BETWEEN :startDate AND :endDate))")
    boolean gameBorrowed(@Param("game") Game game,
            @Param("endDate") LocalDate endDate, @Param("startDate") LocalDate startDate);

    @Query("SELECT COUNT(l) > 0 FROM Loan l WHERE l.client = :client AND ((l.startDate <= :endDate AND l.endDate >= :startDate) OR (l.startDate BETWEEN :startDate AND :endDate) OR (l.endDate BETWEEN :startDate AND :endDate))")
    boolean loanCountByClientAndDate(@Param("client") Client client,
            @Param("endDate") LocalDate endDate, @Param("startDate") LocalDate startDate);

}
