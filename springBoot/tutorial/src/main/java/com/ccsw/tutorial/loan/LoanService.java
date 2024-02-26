package com.ccsw.tutorial.loan;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

public interface LoanService {
    // paginado
    Page<Loan> findPage(LoanSearchDto dto); // recuperar listado paginado de Loan

    void delete(Long id) throws Exception; // eliminar un Préstamo

    // filtrado
    Loan get(Long id);

    // List<Loan> find(String game, Long client); // recupera Loans filtrando
    // opcionalmente por título, nombre y/o fecha
    List<Loan> findAll();

    void save(Long id, LoanDto dto) throws Exception; // crear o actualizar un Préstamo
}
