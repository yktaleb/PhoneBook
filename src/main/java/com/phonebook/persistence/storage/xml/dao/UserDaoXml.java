package com.phonebook.persistence.storage.xml.dao;

import com.phonebook.model.User;
import com.phonebook.model.XmlDatabase;
import com.phonebook.persistence.UserDao;
import com.phonebook.persistence.exceptions.XmlDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoXml implements UserDao {
    @Autowired
    private XmlWorker xmlWorker;

    @Override
    public User add(User user) {
        try {
            List<User> allUsers = xmlWorker.getAllUsers();
            int size = allUsers.size();
            if (size != 0) {
                user.setUserId((long) size);
            } else {
                user.setUserId(allUsers.get(size - 1).getUserId());
            }
            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getUsers().add(user);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User find(Long id) {
        try {
            for (User user : xmlWorker.getAllUsers()) {
                if (id.equals(user.getUserId())) {
                    return user;
                }
            }
        } catch (XmlDatabaseException e) {
            //TODO
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        try {
            return xmlWorker.getAllUsers();
        } catch (XmlDatabaseException e) {
            return null;
        }
    }

    @Override
    public User update(User user) {
        try {
            Integer index = getIndexInUsers(user);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            if (index != null) {
                objectDatabaseForUpdate.getUsers().set(index, user);
                xmlWorker.update(objectDatabaseForUpdate);
            } else {
                //TODO
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void delete(Long userId) {
        try {
            Integer index = getIndexInUsers(userId);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getUsers().remove(index);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            for (User user : xmlWorker.getAllUsers()) {
                if (login.equals(user.getLogin())) {
                    return Optional.ofNullable(user);
                }
            }
        } catch (XmlDatabaseException e) {
            //TODO
        }
        return Optional.empty();
    }

    @Override
    public void deleteUserRoles(Long userId) {
        try {
            Integer index = getIndexInUsers(userId);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getUsers().get(index).setRoles(null);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void persistUserRoles(User user) {
        try {
            Integer index = getIndexInUsers(user);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getUsers().get(index).setRoles(user.getRoles());
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User updateGeneralInformation(User user) {
        try {
            Integer index = getIndexInUsers(user);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            if (index != null) {
                user.setPassword(xmlWorker.getAllUsers().get(index).getPassword());
                user.setRoles(xmlWorker.getAllUsers().get(index).getRoles());
                objectDatabaseForUpdate.getUsers().set(index, user);
                xmlWorker.update(objectDatabaseForUpdate);
            } else {
                //TODO
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User updatePassword(User user) {
        try {
            Integer index = getIndexInUsers(user);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            if (index != null) {
                objectDatabaseForUpdate.getUsers().get(index).setPassword(user.getPassword());
                xmlWorker.update(objectDatabaseForUpdate);
            } else {
                //TODO
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Integer getIndexInUsers(User user) throws XmlDatabaseException {
        Integer index = null;
        List<User> allUsers = xmlWorker.getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            if (user.getUserId().equals(allUsers.get(i).getUserId())) {
                index = i;
            }
        }
        return index;
    }

    private Integer getIndexInUsers(Long userId) throws XmlDatabaseException {
        Integer index = null;
        List<User> allUsers = xmlWorker.getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            if (userId.equals(allUsers.get(i).getUserId())) {
                index = i;
            }
        }
        return index;
    }
}
