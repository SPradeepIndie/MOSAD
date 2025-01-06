package org.rtss.mosad_backend.dto.credit_dtos;

import org.rtss.mosad_backend.entity.credit.Credit;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RepaymentDTO {
    private Long repaymentId;
    private Date date;
    private double amount;


    public RepaymentDTO() {

    }

    public RepaymentDTO(Long repaymentId, Date date, double amount) {
        this.repaymentId = repaymentId;
        this.date = date;
        this.amount = amount;

    }

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


}
