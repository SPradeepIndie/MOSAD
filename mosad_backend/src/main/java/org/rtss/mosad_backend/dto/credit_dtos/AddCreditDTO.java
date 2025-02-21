package org.rtss.mosad_backend.dto.credit_dtos;

import java.util.Date;

public class AddCreditDTO {
    private Long customerId;
    private Long billId;
    private double balance;
    private Date dueDate;

    public AddCreditDTO() {
    }

    public AddCreditDTO(Long customerId, Long billId, double balance, Date dueDate) {
        this.customerId = customerId;
        this.billId = billId;
        this.balance = balance;
        this.dueDate = dueDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
