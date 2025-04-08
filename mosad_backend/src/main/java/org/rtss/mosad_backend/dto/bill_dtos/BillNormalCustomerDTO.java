package org.rtss.mosad_backend.dto.bill_dtos;

public class BillNormalCustomerDTO {
    private Long customerId;

    private String customerName;

    public BillNormalCustomerDTO() {
    }

    public BillNormalCustomerDTO(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
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
}
