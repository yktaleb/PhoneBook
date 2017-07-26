package com.phonebook.controller;

import com.phonebook.model.User;
import com.phonebook.service.AutoLoginService;
import com.phonebook.service.RegistrationService;
import com.phonebook.service.exceptions.IncorrectUserDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AutoLoginService autoLoginService;

    @RequestMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map registration(@RequestBody User user) {

        Map<String, String> result = new HashMap();

        try {
            registrationService.validation(user);
            registrationService.register(user);
            autoLoginService.autologin(user.getLogin(), user.getPassword());
            result.put("status", "success");
        } catch (IncorrectUserDataException e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
        }

        return result;
    }
}
