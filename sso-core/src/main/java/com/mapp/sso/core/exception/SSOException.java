package com.mapp.sso.core.exception;

public class SSOException extends RuntimeException {

    private String msg;

    public SSOException(String msg) {
        this.msg = msg;
    }

    public SSOException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public SSOException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }
}
