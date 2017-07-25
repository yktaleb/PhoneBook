package com.phonebook.service.exceptions;

public class IncorrectUserDataException extends Exception{
    public IncorrectUserDataException(String message) {
        super(message);
    }
}
