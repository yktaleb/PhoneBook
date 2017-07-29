package com.phonebook.persistence.storage.xml.dao;


import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.model.XmlDatabase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(Contact.builder().firstName("A").build());
        contacts.add(Contact.builder().firstName("D").build());
        contacts.add(Contact.builder().firstName("B").build());
        contacts.add(Contact.builder().firstName("C").build());
        System.out.println(contacts);


//        Collections.sort(contacts, new Comparator<Contact>() {
//            @Override
//            public int compare(Contact o1, Contact o2) {
//                return o1.getFirstName().compareTo(o2.getFirstName());
//            }
//        });
        contacts.sort(Comparator.comparing(Contact::getFirstName));


        System.out.println(contacts);
    }
}
