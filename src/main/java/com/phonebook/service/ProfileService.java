package com.phonebook.service;

import com.phonebook.model.User;
import com.phonebook.service.exceptions.IncorrectUserDataException;

public interface ProfileService {

    User updateGeneralInformation(User user) throws IncorrectUserDataException;

    User updatePassword(User user, String currentPassword, String newPassword) throws IncorrectUserDataException;

    void validationGeneralInformation(User user) throws IncorrectUserDataException;
}
