package org.rtss.mosad_backend.dto.bill_dtos;

import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;

import java.util.Date;
import java.util.List;

public class BillDTO {
    private Long id;
    private Double totalAmount;
    private Double advance;
    private Double balance;
    private Date date;
    private Long customerId;
    private List<BillItemDTO> items;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<BillItemDTO> getItems() {
        return items;
    }

    public void setItems(List<BillItemDTO> items) {
        this.items = items;
    }
}
