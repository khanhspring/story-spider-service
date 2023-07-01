package com.webspider.infrastructure.exception;

public class PageHasBeenRedirectedException extends ApplicationException {
    public PageHasBeenRedirectedException() {
        super("Page has been redirected");
    }
}
