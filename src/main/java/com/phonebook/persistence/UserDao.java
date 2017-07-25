package com.phonebook.persistence;

import com.phonebook.model.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User> {

    Optional<User> findByLogin(String login);

    void deleteUserRoles(Long userId);

    void persistUserRoles(User user);
}
