package com.phonebook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact {
    @XmlElement
    private Long contactId;

    @XmlElement
    private String lastName;

    @XmlElement
    private String firstName;

    @XmlElement
    private String patronymicName;

    @XmlElement
    private String mobilePhone;

    @XmlElement
    private String homePhone;

    @XmlElement
    private String address;

    @XmlElement
    private String email;

    @XmlElement
    private Long userId;
}
