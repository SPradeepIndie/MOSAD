package org.rtss.mosad_backend.dto.retail_management;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentHistoryDTO {

    private Date date;
    private String description;
    private String paymentStatus;
    private Double amount;

    public PaymentHistoryDTO(Date date, String description, String paymentStatus, Double amount) {
        this.date = date;
        this.description = description;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    public PaymentHistoryDTO() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
