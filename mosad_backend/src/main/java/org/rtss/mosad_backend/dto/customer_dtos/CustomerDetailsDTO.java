package org.rtss.mosad_backend.dto.customer_dtos;

import jakarta.validation.constraints.NotEmpty;

public class CustomerDetailsDTO {
    private CustomerDTO customerDTO;

    @NotEmpty(message = "A customer must have at least one contact.")
    private CustomerContactDTO customerContactDTO;
    
    private Long customerId;
    private Long userId;

    public CustomerDetailsDTO(CustomerDTO customerDTO, CustomerContactDTO customerContactDTO, Long customerId, Long userId) {
        this.customerDTO = customerDTO;
        this.customerContactDTO = customerContactDTO;
        this.customerId = customerId;
        this.userId = userId;
        
    }

    public CustomerDetailsDTO() {
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CustomerContactDTO getCustomerContactDTO() {
        return customerContactDTO;
    }

    public void setCustomerContactDTO(CustomerContactDTO customerContactDTO) {
        this.customerContactDTO = customerContactDTO;
        
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;  
        
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
