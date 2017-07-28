package com.phonebook.service;

import com.phonebook.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService{
    Optional<User> findByLogin(String login);
    User createUser(String lastName, String firstName, String patronymicName, String login, String password, List<String> roles);
    User updateGeneralInformation(User user);
    User updatePassword(User user);
    User getCurrentUser();
}
