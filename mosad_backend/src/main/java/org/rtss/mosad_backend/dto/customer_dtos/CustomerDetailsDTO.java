package org.rtss.mosad_backend.dto.customer_dtos;

import jakarta.validation.constraints.NotEmpty;

public class CustomerDetailsDTO {
    private CustomerDTO customerDTO;

    @NotEmpty(message = "A customer must have at least one contact.")
    private CustomerContactDTO customerContactDTO;

    public CustomerDetailsDTO(CustomerDTO customerDTO, CustomerContactDTO customerContactDTO) {
        this.customerDTO = customerDTO;
        this.customerContactDTO = customerContactDTO;
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
}
