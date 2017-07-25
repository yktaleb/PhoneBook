package com.phonebook.controller;

import com.phonebook.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralPagesController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("login")
    public String showLogin() {
        return "login";
    }

}
