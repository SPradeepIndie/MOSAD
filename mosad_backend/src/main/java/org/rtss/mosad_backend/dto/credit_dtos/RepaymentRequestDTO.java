package org.rtss.mosad_backend.dto.credit_dtos;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RepaymentRequestDTO {
    private Long creditId; // ID of the associated credit
    private Date date;
    private double amount;

    public RepaymentRequestDTO() {}

    public RepaymentRequestDTO(Long creditId, Date date, double amount) {
        this.creditId = creditId;
        this.date = date;
        this.amount = amount;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
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
}
