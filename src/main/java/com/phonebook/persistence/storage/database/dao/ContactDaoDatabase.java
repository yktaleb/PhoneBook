package com.phonebook.persistence.storage.database.dao;

import com.phonebook.model.Contact;
import com.phonebook.persistence.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactDaoDatabase extends AbstractDao implements ContactDao {
    @Autowired
    public ContactDaoDatabase(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public Contact add(Contact contact) {
        String insertQuery = "INSERT INTO `contact` (`last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Long contactId = executeInsertWithId(insertQuery, contact.getLastName(), contact.getFirstName(), contact.getPatronymicName(),
                contact.getMobilePhone(), contact.getHomePhone(), contact.getAddress(), contact.getEmail(), contact.getUserId());

        contact.setContactId(contactId);

        return contact;
    }

    @Override
    @Transactional
    public Contact find(Long id) {
        String findOneQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id` FROM `contact` WHERE `contact_id` = ?";

        return findOne(findOneQuery, new ContactRowMapper(), id);
    }

    @Override
    @Transactional
    public List<Contact> findAll() {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id` FROM `contact`";

        return findMultiple(findAllQuery, new ContactRowMapper());
    }

    @Override
    @Transactional
    public Contact update(Contact contact) {
        String updateQuery = "UPDATE `contact` SET `last_name` = ?, `first_name` = ?, `patronymic_name` = ?, " +
                "`mobile_phone` = ?, `home_phone` = ?, `address` = ?, `email` = ?, `user_id` = ? WHERE `contact_id` = ?";

        executeUpdate(updateQuery, contact.getLastName(), contact.getFirstName(), contact.getPatronymicName(),
                contact.getMobilePhone(), contact.getHomePhone(), contact.getAddress(), contact.getEmail(), contact.getUserId(), contact.getContactId());

        return contact;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM `contact` WHERE `contact_id` = ?";

        executeUpdate(deleteQuery, id);
    }

    @Override
    @Transactional
    public List<Contact> findByUserId(Long userId) {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id` FROM `contact` WHERE user_id = ?";

        return findMultiple(findAllQuery, new ContactRowMapper(), userId);
    }

    @Override
    @Transactional
    public List<Contact> findSortedByFirstName(Long userId) {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id` FROM `contact` WHERE user_id = ? " +
                "ORDER BY first_name";

        return findMultiple(findAllQuery, new ContactRowMapper(), userId);
    }

    @Override
    @Transactional
    public List<Contact> findSortedByLastName(Long userId) {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id` FROM `contact` WHERE user_id = ? " +
                "ORDER BY last_name";

        return findMultiple(findAllQuery, new ContactRowMapper(), userId);
    }

    @Override
    @Transactional
    public List<Contact> findSortedByMobilePhone(Long userId) {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `address`, `email`, `user_id` FROM `contact` WHERE user_id = ? " +
                "ORDER BY mobile_phone";

        return findMultiple(findAllQuery, new ContactRowMapper(), userId);
    }

    private class ContactRowMapper implements RowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {

            return Contact.builder()
                    .contactId(rs.getLong("contact_id"))
                    .lastName(rs.getString("last_name"))
                    .firstName(rs.getString("first_name"))
                    .patronymicName(rs.getString("patronymic_name"))
                    .mobilePhone(rs.getString("mobile_phone"))
                    .homePhone(rs.getString("home_phone"))
                    .address(rs.getString("address"))
                    .email(rs.getString("email"))
                    .userId(rs.getLong("user_id"))
                    .build();
        }
    }


}
