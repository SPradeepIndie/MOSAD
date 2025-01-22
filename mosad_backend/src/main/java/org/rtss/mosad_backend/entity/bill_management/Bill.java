package org.rtss.mosad_backend.entity.bill_management;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.user_management.Users;

import java.util.Date;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;
    private Double advance;
    private Double balance;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Constructors
    public Bill(Double totalAmount, Double advance, Double balance, Date date, Users user, Customer customer) {
        this.totalAmount = totalAmount;
        this.advance = advance;
        this.balance = balance;
        this.date = date;
        this.user = user;
        this.customer = customer;
    }

    public Bill() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
