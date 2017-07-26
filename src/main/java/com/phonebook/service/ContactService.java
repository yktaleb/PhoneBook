package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.service.exceptions.IncorrectContactDataException;

public interface ContactService {
    Contact add(Contact contact) throws IncorrectContactDataException;
    void validation(Contact contact) throws IncorrectContactDataException;
}
