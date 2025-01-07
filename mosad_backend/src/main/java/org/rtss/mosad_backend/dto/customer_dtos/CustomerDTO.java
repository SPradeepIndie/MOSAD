package org.rtss.mosad_backend.dto.customer_dtos;

import jakarta.validation.constraints.NotEmpty;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;

import java.util.List;

public class CustomerDTO {
    private Long id;
    private String name;
    @NotEmpty(message = "A customer must have at least one contact.")
    private List<CustomerContactDTO> contacts;
    private List<CreditDTO> credits;

    private String customerType;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomerContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<CustomerContactDTO> contacts) {
        this.contacts = contacts;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public List<CreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }
}

