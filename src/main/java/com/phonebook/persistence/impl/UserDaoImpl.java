package com.phonebook.persistence.impl;

import com.phonebook.model.User;
import com.phonebook.persistence.UserDao;
import com.phonebook.persistence.storage.database.dao.UserDaoDatabase;
import com.phonebook.persistence.storage.xml.dao.UserDaoXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Value("${storage.type}")
    private String storageType;

    private final String DATABASE = "database";
    private final String XML = "xml";

    @Autowired
    private UserDaoDatabase userDaoDatabase;
    @Autowired
    private UserDaoXml userDaoXml;

    @Override
    public User add(User user) {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.add(user);
        } else {
            return userDaoXml.add(user);
        }
    }

    @Override
    public User find(Long id) {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.find(id);
        } else {
            return userDaoXml.find(id);
        }
    }

    @Override
    public List<User> findAll() {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.findAll();
        } else {
            return userDaoXml.findAll();
        }
    }

    @Override
    public User update(User user) {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.update(user);
        } else {
            return userDaoXml.update(user);
        }
    }

    @Override
    public void delete(Long id) {
        if (storageType.equals(DATABASE)) {
            userDaoDatabase.delete(id);
        } else {
            userDaoXml.delete(id);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.findByLogin(login);
        } else {
            return userDaoXml.findByLogin(login);
        }
    }

    @Override
    public void deleteUserRoles(Long userId) {
        if (storageType.equals(DATABASE)) {
            userDaoDatabase.deleteUserRoles(userId);
        } else {

        }
    }

    @Override
    public void persistUserRoles(User user) {
        if (storageType.equals(DATABASE)) {
            userDaoDatabase.persistUserRoles(user);
        } else {
            userDaoXml.persistUserRoles(user);
        }
    }

    @Override
    public User updateGeneralInformation(User user) {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.updateGeneralInformation(user);
        } else {
            return userDaoXml.updateGeneralInformation(user);
        }
    }

    @Override
    public User updatePassword(User user) {
        if (storageType.equals(DATABASE)) {
            return userDaoDatabase.updatePassword(user);
        } else {
            return userDaoXml.updatePassword(user);
        }
    }
}
