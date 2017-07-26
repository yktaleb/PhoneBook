package com.phonebook.service.impl;

import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.UserDao;
import com.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("user was not found!"));
        user.setRoles(roleDao.findUserRolesById(user.getUserId()));
        return user;
    }

    @Override
    public User createUser(String lastName, String firstName, String patronymicName, String login, String password, List<String> roles) {
        User user = User.builder()
                .lastName(lastName)
                .firstName(firstName)
                .patronymicName(patronymicName)
                .login(login)
                .password(bCryptPasswordEncoder.encode(password))
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

    @Override
    public User updateGeneralInformation(User user) {
        userDao.updateGeneralInformation(user);
        return user;
    }

    @Override
    public User updatePassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.updatePassword(user);
        return user;
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            return userDao.findByLogin(username).orElse(null);
        } else {
            return null;
        }

    }
}
