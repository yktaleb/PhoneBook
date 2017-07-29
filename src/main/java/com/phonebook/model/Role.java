package com.phonebook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.xml.bind.annotation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Role implements GrantedAuthority{

    @XmlElement
    private Long roleId;

    @XmlElement
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
