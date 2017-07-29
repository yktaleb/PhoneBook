package com.phonebook.persistence.storage.xml.dao;

import com.phonebook.model.Contact;
import com.phonebook.model.XmlDatabase;
import com.phonebook.persistence.ContactDao;
import com.phonebook.persistence.exceptions.XmlDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class ContactDaoXml implements ContactDao {

    @Autowired
    private XmlWorker xmlWorker;

    @Override
    public Contact add(Contact contact) {
        try {
            List<Contact> allContacts = xmlWorker.getAllContacts();
            int size = allContacts.size();
            if (size != 0) {
                contact.setContactId((long) size);
            } else {
                contact.setContactId(allContacts.get(size - 1).getContactId());
            }
            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getContacts().add(contact);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return contact;
    }

    @Override
    public Contact find(Long id) {
        try {
            for (Contact contact : xmlWorker.getAllContacts()) {
                if (id.equals(contact.getContactId())) {
                    return contact;
                }
            }
        } catch (XmlDatabaseException e) {
            //TODO
        }
        return null;
    }

    @Override
    public List<Contact> findAll() {
        try {
            return xmlWorker.getAllContacts();
        } catch (XmlDatabaseException e) {
            return null;
        }
    }

    @Override
    public Contact update(Contact contact) {
        try {
            Integer index = getIndexInContacts(contact);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            if (index != null) {
                objectDatabaseForUpdate.getContacts().set(index, contact);
                xmlWorker.update(objectDatabaseForUpdate);
            } else {
                //TODO
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return contact;
    }

    @Override
    public void delete(Long contactId) {
        try {
            Integer index = getIndexInContacts(contactId);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getContacts().remove(index);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> findByUserId(Long userId) {
        List<Contact> userContacts = new ArrayList<>();
        try {
            for (Contact contact : xmlWorker.getAllContacts()) {
                if (userId.equals(contact.getUserId())) {
                    userContacts.add(contact);
                }
            }
        } catch (XmlDatabaseException e) {
            //TODO
        }
        return userContacts;
    }

    @Override
    public List<Contact> findSortedByFirstName(Long userId) {
        List<Contact> contactList = null;

        contactList = findByUserId(userId);
        contactList.sort(Comparator.comparing(Contact::getFirstName));


        return contactList;
    }

    @Override
    public List<Contact> findSortedByLastName(Long userId) {
        List<Contact> contactList = null;

        contactList = findByUserId(userId);
        contactList.sort(Comparator.comparing(Contact::getLastName));


        return contactList;
    }

    @Override
    public List<Contact> findSortedByMobilePhone(Long userId) {
        List<Contact> contactList = null;

        contactList = findByUserId(userId);
        contactList.sort(Comparator.comparing(Contact::getMobilePhone));


        return contactList;
    }


    private Integer getIndexInContacts(Contact contact) throws XmlDatabaseException {
        Integer index = null;
        List<Contact> allContacts = xmlWorker.getAllContacts();
        for (int i = 0; i < allContacts.size(); i++) {
            if (contact.getContactId().equals(allContacts.get(i).getContactId())) {
                index = i;
            }
        }
        return index;
    }

    private Integer getIndexInContacts(Long contactId) throws XmlDatabaseException {
        Integer index = null;
        List<Contact> allContacts = xmlWorker.getAllContacts();
        for (int i = 0; i < allContacts.size(); i++) {
            if (contactId.equals(allContacts.get(i).getContactId())) {
                index = i;
            }
        }
        return index;
    }
}
