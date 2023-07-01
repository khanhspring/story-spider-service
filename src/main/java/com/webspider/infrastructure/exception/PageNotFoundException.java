package com.webspider.infrastructure.exception;

public class PageNotFoundException extends ApplicationException {
    public PageNotFoundException() {
        super("Page not found");
    }
}
