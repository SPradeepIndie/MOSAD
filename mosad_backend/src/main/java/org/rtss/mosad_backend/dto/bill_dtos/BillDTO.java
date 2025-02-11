package org.rtss.mosad_backend.dto.bill_dtos;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;

import java.time.LocalDate;
import java.util.List;

public class BillDTO {
    private Long billId;
    private Double totalAmount;
    private Double advance;
    private Double balance;
    private LocalDate date;
    private CustomerDTO customerDTO;
    private List<BillItemDTO> billItemDTO;

    // Getters and Setters
    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<BillItemDTO> getBillItemDTO() {
        return billItemDTO;
    }

    public void setBillItemDTO(List<BillItemDTO> billItemDTO) {
        this.billItemDTO = billItemDTO;
    }
}
