package org.rtss.mosad_backend.dto.customer_dtos;

import jakarta.validation.constraints.NotEmpty;
import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;

import java.util.List;

public class CustomerDTO {
    private Long customerId;

    private String customerName;

    @NotEmpty(message = "A customer must have at least one contact.")
    private CustomerContactDTO customerContactDTO;

    private List<CreditDTO> credits;

    private String customerType;

    // Getters and Setters


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CustomerContactDTO getCustomerContactDTO() {
        return customerContactDTO;
    }

    public void setCustomerContactDTO(CustomerContactDTO customerContactDTO) {
        this.customerContactDTO = customerContactDTO;
    }

    public List<CreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
