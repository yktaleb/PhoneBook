package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.service.exceptions.IncorrectContactDataException;

import java.util.List;

public interface ContactService {
    List<Contact> getUserContacts();
    Contact add(Contact contact) throws IncorrectContactDataException;
    void validation(Contact contact) throws IncorrectContactDataException;
}
