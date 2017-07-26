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
    private String firstName;
    private String patronymicName;
    private String mobilePhone;
    private String homePhone;
    private String googlePlaceId;
    private String email;
    private Long userId;
}
