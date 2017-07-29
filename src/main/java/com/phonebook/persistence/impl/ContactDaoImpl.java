package com.phonebook.persistence.impl;

import com.phonebook.model.Contact;
import com.phonebook.persistence.ContactDao;
import com.phonebook.persistence.storage.database.dao.ContactDaoDatabase;
import com.phonebook.persistence.storage.xml.dao.ContactDaoXml;
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
    @Autowired
    private ContactDaoXml contactDaoXml;

    @Override
    public Contact add(Contact contact) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.add(contact);
        } else {
            return contactDaoXml.add(contact);
        }
    }

    @Override
    public Contact find(Long contactId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.find(contactId);
        } else {
            return contactDaoXml.find(contactId);
        }
    }

    @Override
    public List<Contact> findAll() {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findAll();
        } else {
            return contactDaoXml.findAll();
        }
    }

    @Override
    public Contact update(Contact contact) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.update(contact);
        } else {
            return contactDaoXml.update(contact);
        }
    }

    @Override
    public void delete(Long id) {
        if (storageType.equals(DATABASE)) {
            contactDaoDatabase.delete(id);
        } else {
            contactDaoXml.delete(id);
        }
    }

    @Override
    public List<Contact> findByUserId(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findByUserId(userId);
        } else {
            return contactDaoXml.findByUserId(userId);
        }
    }

    @Override
    public List<Contact> findSortedByFirstName(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findSortedByFirstName(userId);
        } else {
            return contactDaoXml.findSortedByFirstName(userId);
        }
    }

    @Override
    public List<Contact> findSortedByLastName(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findSortedByLastName(userId);
        } else {
            return contactDaoXml.findSortedByLastName(userId);
        }
    }

    @Override
    public List<Contact> findSortedByMobilePhone(Long userId) {
        if (storageType.equals(DATABASE)) {
            return contactDaoDatabase.findSortedByMobilePhone(userId);
        } else {
            return contactDaoXml.findSortedByMobilePhone(userId);
        }
    }
}
