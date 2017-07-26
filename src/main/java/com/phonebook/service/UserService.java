package com.phonebook.service;

import com.phonebook.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{
    User createUser(String lastName, String firstName, String patronymicName, String login, String password, List<String> roles);
    User updateGeneralInformation(User user);
    User updatePassword(User user);
    User getCurrentUser();
}
