package com.phonebook.persistence.impl;

import com.phonebook.model.Contact;
import com.phonebook.persistence.AbstractDao;
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
public class ContactDaoImpl extends AbstractDao implements ContactDao {
    @Autowired
    public ContactDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional
    public Contact add(Contact contact) {
        String insertQuery = "INSERT INTO `contact` (`last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `google_place_id`, `email`, `user_id`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Long contactId = executeInsertWithId(insertQuery, contact.getLastName(), contact.getFirstName(), contact.getPatronymicName(),
                contact.getMobilePhone(), contact.getHomePhone(), contact.getGooglePlaceId(), contact.getEmail(), contact.getUserId());

        contact.setContactId(contactId);

        return contact;
    }

    @Override
    @Transactional
    public Contact find(Long id) {
        String findOneQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `google_place_id`, `email`, `user_id` FROM `contact` WHERE `contact_id` = ?";

        return findOne(findOneQuery, new ContactRowMapper(), id);
    }

    @Override
    @Transactional
    public List<Contact> findAll() {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `google_place_id`, `email`, `user_id` FROM `contact`";

        return findMultiple(findAllQuery, new ContactRowMapper());
    }

    @Override
    @Transactional
    public Contact update(Contact contact) {
        String updateQuery = "UPDATE `contact` SET (`last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `google_place_id`, `email`, `user_id`) WHERE `contact_id` = ?";

        executeUpdate(updateQuery, contact.getLastName(), contact.getFirstName(), contact.getPatronymicName(),
                contact.getMobilePhone(), contact.getHomePhone(), contact.getGooglePlaceId(), contact.getEmail(), contact.getContactId());

        return contact;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM `contact` WHERE `contact_id` = ?";

        executeUpdate(deleteQuery, id);
    }

    @Override
    public List<Contact> findByUserId(Long userId) {
        String findAllQuery = "SELECT `contact_id`, `last_name`, `first_name`, `patronymic_name`, " +
                "`mobile_phone`, `home_phone`, `google_place_id`, `email`, `user_id` FROM `contact` WHERE user_id = ?";

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
                    .googlePlaceId(rs.getString("google_place_id"))
                    .email(rs.getString("email"))
                    .userId(rs.getLong("user_id"))
                    .build();
        }
    }


}
