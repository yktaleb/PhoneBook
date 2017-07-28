package com.phonebook.controller;

import com.phonebook.model.Contact;
import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.service.AutoLoginService;
import com.phonebook.service.ContactService;
import com.phonebook.service.ProfileService;
import com.phonebook.service.UserService;
import com.phonebook.service.exceptions.IncorrectContactDataException;
import com.phonebook.service.exceptions.IncorrectUserDataException;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map add(@RequestBody Contact contact) {

        Map<String, String> response = new HashMap<>();

        contact.setUserId(userService.getCurrentUser().getUserId());

        try {
            contactService.validation(contact);
            contactService.add(contact);
            response.put("message", "Contact saved");
            response.put("status", "success");
        } catch (IncorrectContactDataException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
        }

        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map update(@RequestBody Contact contact) {

        Map<String, String> response = new HashMap<>();

        contact.setUserId(userService.getCurrentUser().getUserId());

        try {
            contactService.validation(contact);
            contactService.update(contact);
            response.put("message", "Contact updated");
            response.put("status", "success");
        } catch (IncorrectContactDataException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
        }

        return response;
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map delete(@RequestParam("contactId") Long contactId) {

        Map<String, String> response = new HashMap<>();

        contactService.delete(contactId);

        response.put("status", "success");

        return response;
    }

    @RequestMapping(value = "/getSort/firstName", produces = "application/json")
    @ResponseBody
    public List<Contact> getSortedByFirstName() {
        return contactService.getSortByFirstName();
    }

    @RequestMapping(value = "/getSort/lastName", produces = "application/json")
    @ResponseBody
    public List<Contact> getSortedByLastName() {
        return contactService.getSortByLastName();
    }

    @RequestMapping(value = "/getSort/mobilePhone", produces = "application/json")
    @ResponseBody
    public List<Contact> getSortedByMobilePhone() {
        return contactService.getSortByMobilePhone();
    }

    @RequestMapping(value = "/all", produces = "application/json")
    @ResponseBody
    public List<Contact> getAllContacts() {
        return contactService.getUserContacts();
    }
}
