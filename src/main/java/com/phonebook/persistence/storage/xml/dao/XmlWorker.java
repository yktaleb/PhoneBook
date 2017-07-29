package com.phonebook.persistence.storage.xml.dao;

import com.phonebook.model.Contact;
import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.model.XmlDatabase;
import com.phonebook.persistence.UserDao;
import com.phonebook.persistence.exceptions.XmlDatabaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class XmlWorker {

    @Value("${storage.xml.file.path}")
    private String path;

    private Node xmlNode;
    private Binder<Node> binder;
    private Document document;

    private final String ROLE_CLIENT = "ROLE_CLIENT";

    @PostConstruct
    public void init() throws IOException {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            XmlDatabase xmlDatabase = XmlDatabase
                    .builder()
                    .users(Collections.singletonList(new User()))
                    .contacts(Collections.singletonList(new Contact()))
                    .roles(Collections.singletonList(new Role(0L, ROLE_CLIENT)))
                    .build();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(XmlDatabase.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(xmlDatabase, file);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    public Document parseDocument() throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);
    }

    public XmlDatabase getObjectDatabaseForUpdate() throws XmlDatabaseException {
        try {
            JAXBContext jc = JAXBContext.newInstance(XmlDatabase.class);

            binder = jc.createBinder();

            binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            document = parseDocument();

            xmlNode = document.getDocumentElement();

            return  (XmlDatabase)binder.updateJAXB(xmlNode);
        } catch (JAXBException|ParserConfigurationException|IOException|SAXException e) {
            throw new XmlDatabaseException(e.getMessage());
        }
    }

    public void update(XmlDatabase xmlDatabase) throws XmlDatabaseException {
        try {
            xmlNode = binder.updateXML(xmlDatabase);

            document.setNodeValue(xmlNode.getNodeValue());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (JAXBException|TransformerException e) {
            throw new XmlDatabaseException(e.getMessage());
        }
    }

    public XmlDatabase getObjectDatabase() throws XmlDatabaseException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlDatabase.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (XmlDatabase) jaxbUnmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            throw new XmlDatabaseException(e.getMessage());
        }
    }

    public List<User> getAllUsers() throws XmlDatabaseException {
        try {
            return getObjectDatabase().getUsers();
        } catch (XmlDatabaseException e) {
            throw new XmlDatabaseException(e.getMessage());
        }
    }

    public List<Role> getAllRoles() throws XmlDatabaseException {
        try {
            return getObjectDatabase().getRoles();
        } catch (XmlDatabaseException e) {
            throw new XmlDatabaseException(e.getMessage());
        }
    }

    public List<Contact> getAllContacts() throws XmlDatabaseException {
        try {
            return getObjectDatabase().getContacts();
        } catch (XmlDatabaseException e) {
            throw new XmlDatabaseException(e.getMessage());
        }
    }

}
