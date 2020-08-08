package com.focusandcode.rest.webservices.lubaproverbs.proverbs.exceptions;

public class ProverbNotFoundException extends RuntimeException {
    public ProverbNotFoundException(String message) {
        super(message);
    }
}
