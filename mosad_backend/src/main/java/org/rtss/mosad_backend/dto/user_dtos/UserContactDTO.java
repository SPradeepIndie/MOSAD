package org.rtss.mosad_backend.dto.user_dtos;

import org.springframework.stereotype.Component;

@Component
public class UserContactDTO{
    private String contactNum;

    public UserContactDTO(String contactNum) {
        this.contactNum = contactNum;
    }

    public UserContactDTO() {
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    @Override
    public String toString() {
        return "UserContactDTO{" +
                "contact_num='" + contactNum + '\'' +
                '}';
    }
}
