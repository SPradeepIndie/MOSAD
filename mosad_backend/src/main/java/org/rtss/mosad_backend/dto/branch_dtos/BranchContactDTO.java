package org.rtss.mosad_backend.dto.branch_dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BranchContactDTO {

    @NotNull(message = "Contact num object can not be null")
    @Pattern(
            regexp = "^(\\+?\\d{10,12})?$",
            message = "Phone number must be between 10 to 12 digits long and can optionally start with a '+' sign."
    )
    private String contactNumber;

    public BranchContactDTO(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public BranchContactDTO() {
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "BranchContactDto{" +
                "contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
