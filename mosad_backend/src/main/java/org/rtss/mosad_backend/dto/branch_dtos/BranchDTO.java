package org.rtss.mosad_backend.dto.branch_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class BranchDTO {

    @NotBlank(message = "Branch name is mandatory")
    @Size(min = 3, max = 30,message = "Username should between 3 and 30 characters long.")
    private String branchName;
    @NotBlank(message = "Address number is mandatory")
    @Size(max = 10)
    private String addressNumber;
    @NotBlank(message = "Street number is mandatory")
    @Size(max = 10)
    private String streetNumber;
    @NotBlank(message = "City name is mandatory")
    private String city;

    public BranchDTO() {
    }

    public BranchDTO(String branchName, String addressNumber, String streetNumber, String city) {
        this.branchName = branchName;
        this.addressNumber = addressNumber;
        this.streetNumber = streetNumber;
        this.city = city;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
