package org.rtss.mosad_backend.entity.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.user_management.Users;

import java.util.Date;
import java.util.List;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    private double balance;

    @Column(columnDefinition = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Users user;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repayment> repayments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id",referencedColumnName = "billId", nullable = false)
    private Bill bill;

    public Credit() {
    }

    public Credit(double balance, Date dueDate, Customer customer, List<Repayment> repayments,Bill bill) {
        this.balance = balance;
        this.dueDate = dueDate;
        this.customer = customer;
        this.repayments = repayments;
        this.bill = bill;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Repayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<Repayment> repayments) {
        this.repayments = repayments;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditId=" + creditId +
                ", balance=" + balance +
                ", dueDate=" + dueDate +
                ", customer=" + customer +
                ", repayments=" + repayments +
                ", bill=" + bill +
                '}';
    }
}
