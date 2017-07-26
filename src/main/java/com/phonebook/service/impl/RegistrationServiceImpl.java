package com.phonebook.service.impl;

import com.phonebook.model.User;
import com.phonebook.service.RegistrationService;
import com.phonebook.service.UserService;
import com.phonebook.service.exceptions.IncorrectUserDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final String LOGIN_IS_EMPTY = "Login is empty";
    private final String FIRST_NAME_IS_EMPTY = "First name is empty";
    private final String LAST_NAME_IS_EMPTY = "Last name is empty";
    private final String PASSWORD_IS_EMPTY = "Password is empty";
    private final String PATRONYMIC_NAME_IS_EMPTY = "Patronymic name is empty";

    private final String INVALID_LOGIN = "Incorrect login address. Minimum length - 3 symbols. Without special characters";
    private final String INCORRECT_PASSWORD = "Incorrect password. Minimum length - 5 symbols";
    private final String LOGIN_ALREADY_EXISTS = "Login already exists";

    private final String ROLE_CLIENT = "ROLE_CLIENT";

    @Autowired
    private UserService userService;

    @Override
    public User register(User user) throws IncorrectUserDataException {
        List<String> roles = new ArrayList<>() ;
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            roles.add(ROLE_CLIENT);
        }else {
            roles = user.getRoles().stream().map(x -> x.getRoleName()).collect(Collectors.toList());
        }
        try {
            user = userService.createUser(user.getLastName(), user.getFirstName(), user.getPatronymicName(), user.getLogin(), user.getPassword(), roles);
        } catch (DuplicateKeyException e) {
            throw new IncorrectUserDataException(LOGIN_ALREADY_EXISTS);
        }
        return user;
    }

    @Override
    public void validation(User user) throws IncorrectUserDataException {
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
        if (user.getPassword().isEmpty()) {
            throw new IncorrectUserDataException(PASSWORD_IS_EMPTY);
        }
        if (!isPasswordValid(user.getPassword())) {
            throw new IncorrectUserDataException(INCORRECT_PASSWORD);
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
        if (login.length() < 3)
            return false;
        final String regex = "^[a-zA-Z0-9!@#$%^&*()_+|~\\-=\\/‘\\{\\}\\[\\]:\";’<>?,./]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(login);
        return m.matches();
    }
}
