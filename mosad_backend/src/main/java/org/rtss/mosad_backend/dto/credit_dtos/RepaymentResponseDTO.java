package org.rtss.mosad_backend.dto.credit_dtos;

import java.util.Date;


public class RepaymentResponseDTO {
    private Long repaymentId;
    private Date date;
    private double amount;
    private Long creditId;

    public RepaymentResponseDTO(Long repaymentId, Date date, double amount, Long creditId) {
        this.repaymentId = repaymentId;
        this.date = date;
        this.amount = amount;
        this.creditId = creditId;
    }

    // Getters and Setters

    public Long getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }
}
