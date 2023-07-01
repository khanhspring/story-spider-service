package com.webspider.infrastructure.exception;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException() {
        super("Forbidden");
    }
}
