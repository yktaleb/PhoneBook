package com.phonebook.service;

import com.phonebook.model.User;
import com.phonebook.service.exceptions.IncorrectUserDataException;

public interface RegistrationService {
    User register(User user) throws IncorrectUserDataException;
    void validation(User user) throws IncorrectUserDataException;
}
