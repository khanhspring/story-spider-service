package com.webspider.infrastructure.exception;

public class JsoupExecutorPoolEmptyException extends PoolEmptyException {
    public JsoupExecutorPoolEmptyException() {
        super("Jsoup executor pool is empty");
    }
}
