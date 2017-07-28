package com.phonebook.persistence;

import com.phonebook.model.Contact;

import java.util.List;

public interface ContactDao extends CrudDao<Contact> {
    List<Contact> findByUserId(Long userId);
    List<Contact> findSortedByFirstName(Long userId);
    List<Contact> findSortedByLastName(Long userId);
    List<Contact> findSortedByMobilePhone(Long userId);
}
