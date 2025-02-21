package org.rtss.mosad_backend.dto.credit_dtos;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreditDTO {
    private Long creditId;
    @NotEmpty(message = "Balance can not be empty")
    private double balance;
    @NotEmpty(message = "Due date can not be empty")
    private Date dueDate;
    @NotEmpty(message = "Customer id can not be empty")
    private Long customerId;



    public CreditDTO() {
    }

    public CreditDTO(Long creditId, double balance, Date dueDate,Long customerId) {
        this.creditId = creditId;
        this.balance = balance;
        this.dueDate = dueDate;
        this.customerId = customerId;

    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
