package org.rtss.mosad_backend.dto.retail_management;

import java.time.LocalDate;


public class PaymentHistoryDTO {

    private LocalDate date;
    private String description;
    private String paymentStatus;
    private Double amount;

    public PaymentHistoryDTO(LocalDate date, String description, String paymentStatus, Double amount) {
        this.date = date;
        this.description = description;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    public PaymentHistoryDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
