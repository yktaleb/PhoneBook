package com.phonebook.persistence;

import com.phonebook.model.Contact;

import java.util.List;

public interface ContactDao extends CrudDao<Contact> {
    List<Contact> findByUserId(Long userId);
}
