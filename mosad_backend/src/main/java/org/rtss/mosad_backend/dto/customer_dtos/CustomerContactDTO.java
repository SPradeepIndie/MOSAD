package org.rtss.mosad_backend.dto.customer_dtos;

import org.rtss.mosad_backend.entity.customer.CustomerContact;

public class CustomerContactDTO {
    private Long customerContactId;
    private String contactNumber;

    public CustomerContactDTO(CustomerContact customerContact) {
        this.customerContactId = customerContact.getCustomerContactId();
        this.contactNumber = customerContact.getContactNumber();
    }

    public CustomerContactDTO() {
    }

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
