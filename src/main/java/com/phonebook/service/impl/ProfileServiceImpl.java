package com.phonebook.service.impl;

import com.phonebook.model.User;
import com.phonebook.service.ProfileService;
import com.phonebook.service.UserService;
import com.phonebook.service.exceptions.IncorrectUserDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String LOGIN_IS_EMPTY = "Login is empty";
    private final String FIRST_NAME_IS_EMPTY = "First name is empty";
    private final String LAST_NAME_IS_EMPTY = "Last name is empty";
    private final String PATRONYMIC_NAME_IS_EMPTY = "Patronymic name is empty";

    private final String INVALID_LOGIN = "Incorrect login address. Minimum length - 3 symbols. Without special characters";
    private final String LOGIN_ALREADY_EXISTS = "Login already exists";
    private final String INCORRECT_NEW_PASSWORD = "Incorrect new password. Minimum length - 5 symbols";
    private final String INVALID_CURRENT_PASSWORD = "Invalid current password";

    @Autowired
    private UserService userService;

    @Override
    public User updateGeneralInformation(User user) throws IncorrectUserDataException {
        try {
            userService.updateGeneralInformation(user);
        } catch (DuplicateKeyException e) {
            throw new IncorrectUserDataException(LOGIN_ALREADY_EXISTS);
        }
        return user;
    }

    @Override
    public User updatePassword(User user, String currentPassword, String newPassword) throws IncorrectUserDataException {
        if (!encoder.matches(currentPassword, user.getPassword()))
            throw new IncorrectUserDataException(INVALID_CURRENT_PASSWORD);

        if (!isPasswordValid(newPassword))
            throw new IncorrectUserDataException(INCORRECT_NEW_PASSWORD);

        user.setPassword(newPassword);

        userService.updatePassword(user);

        return user;
    }

    public void validationGeneralInformation(User user) throws IncorrectUserDataException {
        if (user.getLastName().isEmpty()) {
            throw new IncorrectUserDataException(LAST_NAME_IS_EMPTY);
        }
        if (user.getFirstName().isEmpty()) {
            throw new IncorrectUserDataException(FIRST_NAME_IS_EMPTY);
        }
        if (user.getPatronymicName().isEmpty()) {
            throw new IncorrectUserDataException(PATRONYMIC_NAME_IS_EMPTY);
        }
        if (user.getLogin().isEmpty()) {
            throw new IncorrectUserDataException(LOGIN_IS_EMPTY);
        }
        if (!isLoginValid(user.getLogin())) {
            throw new IncorrectUserDataException(INVALID_LOGIN);
        }
    }

    boolean isPasswordValid(String password) {
        if (password.length() < 5)
            return false;
        return true;
    }

    boolean isLoginValid(String login) {
        if (login.length() < 3) {
            return false;
        }
        final String regex = "^[a-zA-Z0-9!@#$%^&*()_+|~\\-=\\/‘\\{\\}\\[\\]:\";’<>?,./]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(login);
        return m.matches();
    }

}
