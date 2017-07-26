package com.phonebook.service.impl;

import com.phonebook.model.Contact;
import com.phonebook.persistence.ContactDao;
import com.phonebook.service.ContactService;
import com.phonebook.service.UserService;
import com.phonebook.service.exceptions.IncorrectContactDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final String FIRST_NAME_IS_EMPTY = "First name is empty";
    private final String LAST_NAME_IS_EMPTY = "Last name is empty";
    private final String PATRONYMIC_NAME_IS_EMPTY = "Patronymic name is empty";
    private final String MOBILE_PHONE_IS_EMPTY = "Mobile phone is empty";
    private final String INCORRECT_PHONE = "Incorrect phone number. Example: +380(66)1234567";
    private final String INVALID_EMAIL = "Incorrect email address";

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private UserService userService;

    @Override
    public List<Contact> getUserContacts() {
        return contactDao.findByUserId(userService.getCurrentUser().getUserId());
    }

    @Override
    public Contact add(Contact contact) {
        contactDao.add(contact);
        return null;
    }

    @Override
    public void validation(Contact contact) throws IncorrectContactDataException {
        if ("".equals(contact.getGooglePlaceId())) {
            contact.setGooglePlaceId(null);
        }
        if ("".equals(contact.getHomePhone())) {
            contact.setHomePhone(null);
        }
        if ("".equals(contact.getEmail())) {
            contact.setEmail(null);
        }
        if (contact.getLastName().isEmpty()) {
            throw new IncorrectContactDataException(LAST_NAME_IS_EMPTY);
        }
        if (contact.getFirstName().isEmpty()) {
            throw new IncorrectContactDataException(FIRST_NAME_IS_EMPTY);
        }
        if (contact.getPatronymicName().isEmpty()) {
            throw new IncorrectContactDataException(PATRONYMIC_NAME_IS_EMPTY);
        }
        if (contact.getMobilePhone().isEmpty()) {
            throw new IncorrectContactDataException(MOBILE_PHONE_IS_EMPTY);
        }
        if (!isPhoneNumberValid(contact.getMobilePhone())) {
            throw new IncorrectContactDataException(INCORRECT_PHONE);
        }
        if (contact.getHomePhone() != null && !isPhoneNumberValid(contact.getHomePhone())) {
            throw new IncorrectContactDataException(INCORRECT_PHONE);
        }
        if (contact.getEmail() != null && !isEmailValid(contact.getEmail())) {
            throw new IncorrectContactDataException(INVALID_EMAIL);
        }
    }

    boolean isEmailValid(String email) {
        final String regex = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`\\{|\\}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    boolean isPhoneNumberValid(String phone) {
        final String regex = "^((\\+?380)([0-9]{9}))";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }
}
