package org.rtss.mosad_backend.dto.retail_management;


import java.util.Date;

public class IncompleteTransactionsDTO {

    private Date date;
    private String description;
    private Double balance;
    private Date dueDate;

    public IncompleteTransactionsDTO(Date date, String description, Double balance, Date dueDate) {
        this.date = date;
        this.description = description;
        this.balance = balance;
        this.dueDate = dueDate;
    }

    public IncompleteTransactionsDTO() {
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
