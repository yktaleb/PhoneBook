package com.phonebook.persistence.storage.xml.dao;

import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.model.XmlDatabase;
import com.phonebook.persistence.RoleDao;
import com.phonebook.persistence.exceptions.XmlDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoXml implements RoleDao {
    @Autowired
    private XmlWorker xmlWorker;

    @Override
    public Role add(Role role) {
        try {
            List<Role> allRoles = xmlWorker.getAllRoles();
            int size = allRoles.size();
            if (size != 0) {
                role.setRoleId((long) size);
            } else {
                role.setRoleId(allRoles.get(size).getRoleId());
            }
            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getRoles().add(role);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public Role find(Long id) {
        try {
            for (Role role : xmlWorker.getAllRoles()) {
                if (id.equals(role.getRoleId())) {
                    return role;
                }
            }
        } catch (XmlDatabaseException e) {
            //TODO
        }
        return null;
    }

    @Override
    public List<Role> findAll() {
        try {
            return xmlWorker.getAllRoles();
        } catch (XmlDatabaseException e) {
            return null;
        }
    }

    @Override
    public Role update(Role role) {
        try {
            Integer index = getIndexInRoles(role);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            if (index != null) {
                objectDatabaseForUpdate.getRoles().set(index, role);
                xmlWorker.update(objectDatabaseForUpdate);
            } else {
                //TODO
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public void delete(Long roleId) {
        try {
            Integer index = getIndexInRoles(roleId);

            XmlDatabase objectDatabaseForUpdate = xmlWorker.getObjectDatabaseForUpdate();
            objectDatabaseForUpdate.getRoles().remove(index);
            xmlWorker.update(objectDatabaseForUpdate);
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Role> findUserRolesById(Long userId) {
        try {
            for (User user : xmlWorker.getAllUsers()) {
                if (userId.equals(user.getUserId())) {
                    return user.getRoles();
                }
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Role findByName(String roleName) {
        try {
            for (Role role : xmlWorker.getAllRoles()) {
                if (roleName.equals(role.getRoleName())) {
                    return role;
                }
            }
        } catch (XmlDatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer getIndexInRoles(Role role) throws XmlDatabaseException {
        Integer index = null;
        List<Role> allRoles = xmlWorker.getAllRoles();
        for (int i = 0; i < allRoles.size(); i++) {
            if (role.getRoleId().equals(allRoles.get(i).getRoleId())) {
                index = i;
            }
        }
        return index;
    }

    private Integer getIndexInRoles(Long roleId) throws XmlDatabaseException {
        Integer index = null;
        List<Role> allRoles = xmlWorker.getAllRoles();
        for (int i = 0; i < allRoles.size(); i++) {
            if (roleId.equals(allRoles.get(i).getRoleId())) {
                index = i;
            }
        }
        return index;
    }
}
