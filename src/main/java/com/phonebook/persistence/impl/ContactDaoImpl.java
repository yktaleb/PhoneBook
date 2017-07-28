package com.phonebook.persistence.impl;

import com.phonebook.model.Contact;
import com.phonebook.persistence.ContactDao;
import com.phonebook.persistence.storage.database.dao.ContactDaoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDaoImpl implements ContactDao {

    @Value("${storage.type}")
    private String storageType;

    private final String DATABASE = "database";
    private final String XML = "xml";

    @Autowired
    private ContactDaoDatabase contactDaoDatabase;

    @Override
    public Contact add(Contact entity) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.add(entity);
        } else {
            return null;
        }
    }

    @Override
    public Contact find(Long id) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.find(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Contact> findAll() {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findAll();
        } else {
            return null;
        }
    }

    @Override
    public Contact update(Contact entity) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.update(entity);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        if (storageType.equals(DATABASE)) {
            contactDaoDatabase.delete(id);
        } else {

        }
    }

    @Override
    public List<Contact> findByUserId(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findByUserId(userId);
        } else {
            return null;
        }
    }

    @Override
    public List<Contact> findSortedByFirstName(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findSortedByFirstName(userId);
        } else {
            return null;
        }
    }

    @Override
    public List<Contact> findSortedByLastName(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findSortedByLastName(userId);
        } else {
            return null;
        }
    }

    @Override
    public List<Contact> findSortedByMobilePhone(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findSortedByMobilePhone(userId);
        } else {
            return null;
        }
    }
}
