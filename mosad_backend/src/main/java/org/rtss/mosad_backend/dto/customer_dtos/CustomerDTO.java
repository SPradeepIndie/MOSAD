package org.rtss.mosad_backend.dto.customer_dtos;


public class CustomerDTO {
    private Long customerId;

    private String customerName;

    private String customerType;

    public CustomerDTO(String customerType, String customerName, Long customerId) {
        this.customerType = customerType;
        this.customerName = customerName;
        this.customerId = customerId;

    }

    public CustomerDTO() {
    }


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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
