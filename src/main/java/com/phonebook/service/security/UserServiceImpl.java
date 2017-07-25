package com.phonebook.service.security;

import com.phonebook.model.User;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
