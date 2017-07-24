package com.phonebook.persistence.exceptions;

public class NonUniqueResultException extends RuntimeException {

    public NonUniqueResultException() {
    }

    public NonUniqueResultException(String message) {
        super(message);
    }
}
