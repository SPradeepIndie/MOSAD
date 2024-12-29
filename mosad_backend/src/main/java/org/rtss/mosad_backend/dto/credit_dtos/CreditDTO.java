package org.rtss.mosad_backend.dto.credit_dtos;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreditDTO {
    private Long creditId;

    private double balance;

    private Date dueDate;

    private RepaymentDTO repayments;

    public CreditDTO() {
    }

    public CreditDTO(Long creditId, double balance, Date dueDate, RepaymentDTO repayments) {
        this.creditId = creditId;
        this.balance = balance;
        this.dueDate = dueDate;
        this.repayments = repayments;
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

    public RepaymentDTO getRepayments() {
        return repayments;
    }

    public void setRepayments(RepaymentDTO repayments) {
        this.repayments = repayments;
    }
}
