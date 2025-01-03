package org.rtss.mosad_backend.entity.credit;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repaymentId;
    private Date date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "credit_id", nullable = true)
    private Credit credit;

    public Repayment() {
    }

    public Repayment(Date date, double amount, Credit credit) {
        this.date = date;
        this.amount = amount;
        this.credit = credit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Repayment{" +
                "repaymentId=" + repaymentId +
                ", date=" + date +
                ", amount=" + amount +
                ", credit=" + credit +
                '}';
    }
}
