package com.phonebook.persistence.storage.database.dao;

import com.phonebook.model.Role;
import com.phonebook.model.User;
import com.phonebook.persistence.AbstractDao;
import com.phonebook.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoDatabase extends AbstractDao implements UserDao {

    @Autowired
    public UserDaoDatabase(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public User add(User user) {
        String insertQuery = "INSERT INTO `user` (`last_name`, `first_name`, `patronymic_name`, " +
                "`login`, `password`) VALUES(?, ?, ?, ?, ?)";
        Long userId = executeInsertWithId(insertQuery, user.getLastName(), user.getFirstName(),
                user.getPatronymicName(), user.getLogin(), user.getPassword());
        user.setUserId(userId);

        return user;
    }

    @Override
    @Transactional
    public User find(Long id) {
        String findOneQuery = "SELECT `user_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`login`, `password` FROM `user` WHERE `user_id` = ?";

        return findOne(findOneQuery, new UserRowMapper(), id);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        String findAllQuery = "SELECT `user_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`login`, `password` FROM `user`";

        return findMultiple(findAllQuery, new UserRowMapper());
    }

    @Override
    @Transactional
    public User update(User user) {
        String updateQuery = "UPDATE `user` SET (`last_name` = ?, `first_name` = ?, `patronymic_name` = ?, " +
                "`login` = ?, `password` = ? WHERE `user_id` = ?";

        executeUpdate(updateQuery, user.getLastName(), user.getFirstName(),
                user.getPatronymicName(), user.getLogin(), user.getPassword(), user.getUserId());

        deleteUserRoles(user.getUserId());
        persistUserRoles(user);

        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM `user` WHERE `user_id` = ?";

        executeUpdate(deleteQuery, id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String findOneQuery = "SELECT `user_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`login`, `password` FROM `user` WHERE `login` = ?";

        User user = findOne(findOneQuery, new UserRowMapper(), login);

        return Optional.ofNullable(user);
    }

    @Override
    @Transactional
    public void deleteUserRoles(Long userId) {
        String deleteQuery = "DELETE FROM `user_role` WHERE `user_id` = ?";

        executeUpdate(deleteQuery, userId);
    }

    @Override
    @Transactional
    public void persistUserRoles(User user) {
        String insertQuery = "INSERT INTO `user_role` (`user_id`, `role_id`) VALUES(?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        for (Role role : user.getRoles()) {
            batchArgs.add(new Object[]{user.getUserId(), role.getRoleId()});
        }

        batchUpdate(insertQuery, batchArgs);
    }

    @Override
    @Transactional
    public User updateGeneralInformation(User user) {
        String updateQuery = "UPDATE `user` SET `last_name` = ?, `first_name` = ?, `patronymic_name` = ?, " +
                "`login` = ? WHERE `user_id` = ?";
        executeUpdate(updateQuery, user.getLastName(), user.getFirstName(),
                user.getPatronymicName(), user.getLogin(), user.getUserId());
        return user;
    }

    @Override
    @Transactional
    public User updatePassword(User user) {
        String updateQuery = "UPDATE `user` SET `password` = ? WHERE `login` = ?";
        executeUpdate(updateQuery, user.getPassword(), user.getLogin());
        return user;
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            return User.builder()
                    .userId(rs.getLong("user_id"))
                    .lastName(rs.getString("last_name"))
                    .firstName(rs.getString("first_name"))
                    .patronymicName(rs.getString("patronymic_name"))
                    .login(rs.getString("login"))
                    .password(rs.getString("password"))
                    .build();
        }
    }
}
