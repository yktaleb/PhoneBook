package com.phonebook.controller;

import com.phonebook.model.User;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.UserDao;
import com.phonebook.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AccountController {

    private UserService userService;
    private RoleDao roleDao;

    @Autowired
    public AccountController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @RequestMapping(value = "/api/user/account", method = RequestMethod.GET)
    public Map<String, Object> getName() {
        Map<String, Object> result = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> userOptional = userService.findByLogin(auth.getName());

        if (auth.isAuthenticated() && userOptional.isPresent()) {
            User user = userOptional.get();
            result.put("authenticated", "true");
            result.put("name", user.getFirstName() + " " + user.getLastName());
            result.put("userId", user.getUserId());
            List<Link> profileLinks = new LinkedList<>();

            profileLinks.add(new Link("My profile", "/profile/edit"));

            result.put("profileLinks", profileLinks);
        } else {
            result.put("authenticated", "false");
        }

        return result;
    }

    @Data
    private static class Link {
        Link(String name, String reference) {
            this.name = name;
            this.reference = reference;
        }

        private String name;
        private String reference;
    }
}
