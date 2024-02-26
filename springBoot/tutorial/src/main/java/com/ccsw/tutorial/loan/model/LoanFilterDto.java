package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;

public class LoanFilterDto {
    private Long game;
    private Long client;
    private LocalDate filteredDate;

    public Long getGame() {
        return game;
    }

    public void setGame(Long game) {
        this.game = game;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public LocalDate getFilteredDate() {
        return filteredDate;
    }

    public void setFilteredDate(LocalDate filteredDate) {
        this.filteredDate = filteredDate;
    }
}
