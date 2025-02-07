package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

@Component
public class UserContactDTO{

    @NotNull(message = "Contact num object can not be null")
    @Pattern(
            regexp = "^(\\+?[0-9]{10,12})?$",
            message = "Phone number must be between 10 to 12 digits long and can optionally start with a '+' sign."
    )
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
