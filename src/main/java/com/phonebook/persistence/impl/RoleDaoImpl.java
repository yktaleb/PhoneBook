package com.phonebook.persistence.impl;

import com.phonebook.model.Role;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.storage.database.dao.RoleDaoDatabase;
import com.phonebook.persistence.storage.xml.dao.RoleDaoXml;
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
    @Autowired
    private RoleDaoXml roleDaoXmlo;

    @Override
    public Role add(Role role) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.add(role);
        } else {
            return roleDaoXmlo.add(role);
        }
    }

    @Override
    public Role find(Long id) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.find(id);
        } else {
            return roleDaoXmlo.find(id);
        }
    }

    @Override
    public List<Role> findAll() {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.findAll();
        } else {
            return roleDaoXmlo.findAll();
        }
    }

    @Override
    public Role update(Role role) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.update(role);
        } else {
            return roleDaoXmlo.update(role);
        }
    }

    @Override
    public void delete(Long id) {
        if (storageType.equals(DATABASE)) {
            roleDaoDatabase.delete(id);
        } else {
            roleDaoXmlo.delete(id);
        }
    }

    @Override
    public List<Role> findUserRolesById(Long userId) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.findUserRolesById(userId);
        } else {
            return roleDaoXmlo.findUserRolesById(userId);
        }
    }

    @Override
    public Role findByName(String roleName) {
        if (storageType.equals(DATABASE)) {
            return roleDaoDatabase.findByName(roleName);
        } else {
            return roleDaoXmlo.findByName(roleName);
        }
    }
}
