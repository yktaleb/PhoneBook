package com.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralPagesController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
