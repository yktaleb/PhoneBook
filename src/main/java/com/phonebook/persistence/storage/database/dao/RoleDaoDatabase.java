package com.phonebook.persistence.storage.database.dao;

import com.phonebook.model.Role;
import com.phonebook.persistence.AbstractDao;
import com.phonebook.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleDaoDatabase extends AbstractDao implements RoleDao {

    @Autowired
    public RoleDaoDatabase(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public Role add(Role role) {
        String insertQuery = "INSERT INTO `role` (`role_name`) VALUES (?)";

        Long roleId = executeInsertWithId(insertQuery, role.getRoleName());

        role.setRoleId(roleId);

        return role;
    }

    @Override
    @Transactional
    public Role update(Role role) {
        String updateQuery = "UPDATE `role` SET `role_name` = ? WHERE `role_id` = ?";

        executeUpdate(updateQuery, role.getRoleName(), role.getRoleId());

        return role;
    }

    @Override
    @Transactional
    public Role find(Long id) {
        String findOneQuery = "SELECT `role_id`, `role_name` FROM `role` WHERE `role_id` = ?";

        return findOne(findOneQuery, new RoleRowMapper(), id);
    }

    @Override
    @Transactional
    public List<Role> findAll() {
        String findAllQuery = "SELECT `role_id`, `role_name` FROM `role`";

        return findMultiple(findAllQuery, new RoleRowMapper());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM `role` WHERE `role_id` = ?";
        executeUpdate(deleteQuery, id);
    }

    @Override
    @Transactional
    public List<Role> findUserRolesById(Long userId) {
        String query = "SELECT r.`role_id`, r.`role_name` " +
                "FROM `role` r " +
                "INNER JOIN `user_role` ur " +
                "ON r.`role_id` = ur.`role_id` " +
                "WHERE ur.`user_id` = ?";

        return findMultiple(query, new RoleRowMapper(), userId);
    }

    @Override
    @Transactional
    public Role findByName(String roleName) {
        String query = "SELECT `role_id`, `role_name` FROM `role` WHERE `role_name` = ?";

        return findOne(query, new RoleRowMapper(), roleName);
    }

    private class RoleRowMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Role.builder()
                    .roleId(rs.getLong("role_id"))
                    .roleName(rs.getString("role_name"))
                    .build();
        }
    }
}
