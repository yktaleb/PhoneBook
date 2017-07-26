package com.phonebook.controller;

import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.service.AutoLoginService;
import com.phonebook.service.ProfileService;
import com.phonebook.service.UserService;
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
@RequestMapping("/api/profile")
public class EditProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AutoLoginService loginService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public FrontUser dataTypes() {
        return mapUserToFrontUser(userService.getCurrentUser());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map update(@RequestBody FrontUser frontUser) {

        Map<String, String> response = new HashMap<>();

        User oldUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = mapFrontUserToUser(frontUser);
        user.setUserId(oldUser.getUserId());

        try {
            profileService.validationGeneralInformation(user);
            profileService.updateGeneralInformation(user);
            loginService.autologin(user.getLogin(), oldUser.getPassword());
            response.put("message", "Changes saved");
            response.put("status", "success");
        } catch (IncorrectUserDataException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
        }

        return response;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map changePassword(@RequestParam("currentPassword") String currentPassword,
                              @RequestParam("newPassword") String newPassword) {

        Map<String, String> response = new HashMap<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            profileService.updatePassword(user, currentPassword, newPassword);
            loginService.autologin(user.getLogin(), user.getPassword());
            response.put("message", "Your password has been changed");
            response.put("status", "success");
        } catch (IncorrectUserDataException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
        }

        return response;
    }

    private User mapFrontUserToUser(FrontUser frontUser){
        User user = new User();

        user.setUserId(frontUser.getUserId());
        user.setFirstName(frontUser.getFirstName());
        user.setLastName(frontUser.getLastName());
        user.setPatronymicName(frontUser.getPatronymicName());
        user.setLogin(frontUser.getLogin());
        user.setPassword(frontUser.getPassword());
        user.setRoles(frontUser.getRoles());

        return user;
    }
    private FrontUser mapUserToFrontUser(User user){
        return FrontUser
                .builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymicName(user.getPatronymicName())
                .login(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    @Data
    @Builder
    private static class FrontUser{
        private Long userId;
        private String lastName;
        private String firstName;
        private String patronymicName;
        private String login;
        private String password;

        private List<Role> roles;

    }

}
