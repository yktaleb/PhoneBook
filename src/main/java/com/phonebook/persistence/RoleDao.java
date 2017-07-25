package com.phonebook.persistence;

import com.phonebook.model.Role;

import java.util.List;

public interface RoleDao extends CrudDao<Role> {
    List<Role> findUserRolesById(Long userId);
}
