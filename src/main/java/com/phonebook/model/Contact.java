package com.phonebook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private Long contactId;
    private String lastName;
    private String first_name;
    private String patronymic_name;
    private String mobile_phone;
    private String home_phone;
    private String google_place_id;
    private String email;
}
