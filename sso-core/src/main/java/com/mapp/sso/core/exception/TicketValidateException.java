package com.mapp.sso.core.exception;

public class TicketValidateException extends RuntimeException {

    private String msg;

    public TicketValidateException(String msg) {
        this.msg = msg;
    }

    public TicketValidateException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public TicketValidateException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }
}
