package com.phonebook.controller;

import com.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GeneralPagesController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public String editProfile() {
        return "/profile/editProfile";
    }
}
