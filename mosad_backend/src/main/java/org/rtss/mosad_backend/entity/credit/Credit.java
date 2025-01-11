package org.rtss.mosad_backend.entity.credit;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.customer.Customer;

import java.util.Date;
import java.util.List;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    private double balance;

    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repayment> repayments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id",referencedColumnName = "id")
    private Bill bill;

    public Credit() {
    }

    public Credit(double balance, Date dueDate, Customer customer, List<Repayment> repayments) {
        this.balance = balance;
        this.dueDate = dueDate;
        this.customer = customer;
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

    @Override
    public String toString() {
        return "Credit{" +
                "creditId=" + creditId +
                ", balance=" + balance +
                ", dueDate=" + dueDate +
                ", customer=" + customer +
                ", repayments=" + repayments +
                '}';
    }
}
