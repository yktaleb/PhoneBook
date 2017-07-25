package com.phonebook.service.impl;

import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.UserDao;
import com.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("user was not found!"));
        user.setRoles(roleDao.findUserRolesById(user.getUserId()));
        return user;
    }

    @Override
    public User createUser(String fistName, String lastName, String patronymicName, String login, String password, List<String> roles) {
        User user = User.builder()
                .firstName(fistName)
                .lastName(lastName)
                .patronymicName(patronymicName)
                .login(login)
                .password(password)
                .build();
        List<Role> roleList = new ArrayList<>();

        roles.forEach(roleName -> {
            Role role = roleDao.findByName(roleName);
            if (role != null) {
                roleList.add(role);
            }
        });

        user.setRoles(roleList);

        userDao.add(user);
        userDao.persistUserRoles(user);

        return user;
    }
}
