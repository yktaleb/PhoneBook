package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.service.exceptions.IncorrectContactDataException;

import java.util.List;

public interface ContactService {
    List<Contact> getUserContacts();
    List<Contact> getSortByFirstName();
    List<Contact> getSortByLastName();
    List<Contact> getSortByMobilePhone();
    Contact add(Contact contact);
    Contact update(Contact contact);
    void delete(Long contactId);
    void validation(Contact contact) throws IncorrectContactDataException;
}
