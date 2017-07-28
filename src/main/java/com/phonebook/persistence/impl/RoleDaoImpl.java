package com.phonebook.persistence.impl;

import com.phonebook.model.Role;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.storage.database.dao.RoleDaoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Value("${storage.type}")
    private String storageType;

    private final String DATABASE = "database";
    private final String XML = "xml";

    @Autowired
    private RoleDaoDatabase roleDaoDatabase;

    @Override
    public Role add(Role entity) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.add(entity);
        } else {
            return null;
        }
    }

    @Override
    public Role find(Long id) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.find(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.findAll();
        } else {
            return null;
        }
    }

    @Override
    public Role update(Role entity) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.update(entity);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        if (storageType.equals(DATABASE)) {
            roleDaoDatabase.delete(id);
        } else {

        }
    }

    @Override
    public List<Role> findUserRolesById(Long userId) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.findUserRolesById(userId);
        } else {
            return null;
        }
    }

    @Override
    public Role findByName(String roleName) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.findByName(roleName);
        } else {
            return null;
        }
    }
}
