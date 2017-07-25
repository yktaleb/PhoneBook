package com.phonebook.service;

import com.phonebook.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{
    User createUser(String fistName, String lastName, String patronymicName, String login, String password, List<String> roles);
}
