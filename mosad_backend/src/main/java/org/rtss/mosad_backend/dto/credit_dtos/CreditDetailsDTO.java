package org.rtss.mosad_backend.dto.credit_dtos;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CreditDetailsDTO {

    private Long creditId;
    private String customerName;
    private String contactNumber;
    private double balance;
    private Date dueDate;
    private List<RepaymentDTO> repayments;
    private Long billId;

    public CreditDetailsDTO() {
    }

    public CreditDetailsDTO(Long creditId, String customerName, String contactNumber, double balance, Date dueDate, List<RepaymentDTO> repayments,Long billId) {
        this.creditId = creditId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.balance = balance;
        this.dueDate = dueDate;
        this.repayments = repayments;
        this.billId = billId;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public List<RepaymentDTO> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<RepaymentDTO> repayments) {
        this.repayments = repayments;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }
}


