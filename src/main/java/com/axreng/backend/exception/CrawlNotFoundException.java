package com.axreng.backend.exception;

public class CrawlNotFoundException extends RuntimeException {

    public CrawlNotFoundException(String message) {
        super(message);
    }
}
