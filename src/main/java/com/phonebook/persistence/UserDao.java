package com.phonebook.persistence;

import com.phonebook.model.User;

public interface UserDao extends CrudDao<User> {

    void deleteUserRoles(Long userId);

    void persistUserRoles(User user);
}
