package com.phonebook.service.exceptions;

public class IncorrectContactDataException extends Exception{
    public IncorrectContactDataException(String message) {
        super(message);
    }
}
