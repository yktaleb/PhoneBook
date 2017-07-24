package com.phonebook.model;

import java.util.List;

public class User {
    private Long userId;
    private String login;
    private String password;
    private String lastName;
    private String firstName;
    private String patronymic_name;

    private List<Role> roles;
}
