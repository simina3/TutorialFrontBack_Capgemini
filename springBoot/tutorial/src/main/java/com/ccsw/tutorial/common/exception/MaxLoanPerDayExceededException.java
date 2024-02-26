package com.ccsw.tutorial.common.exception;

public class MaxLoanPerDayExceededException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -8028734648483516195L;

    public MaxLoanPerDayExceededException(String message) {
        super(message);
    }
}
