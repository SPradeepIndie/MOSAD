package org.rtss.mosad_backend.dto.customer_dtos;

public class CustomerContactDTO {
    private Long customerContactId;
    private String contactNumber;

    public Long getCustomerContactId() {
        return customerContactId;
    }

    public void setCustomerContactId(Long customerContactId) {
        this.customerContactId = customerContactId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
