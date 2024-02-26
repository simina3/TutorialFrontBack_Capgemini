package com.ccsw.tutorial.common.exception;

public class DuplicateLoanException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateLoanException(String message) {
        super(message);
    }
}
