package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

public class LoanSearchDto {
    private PageableRequest pageable;
    private LoanFilterDto loanFilterDto;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    public LoanFilterDto getLoanFilterDto() {
        return loanFilterDto;
    }

    public void setLoanFilterDto(LoanFilterDto loanFilterDto) {
        this.loanFilterDto = loanFilterDto;
    }
}
