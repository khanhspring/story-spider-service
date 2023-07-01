package com.webspider.infrastructure.exception;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException() {
        super("Unauthorized");
    }
}
