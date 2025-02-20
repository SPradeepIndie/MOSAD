package org.rtss.mosad_backend.entity.bill_management;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.user_management.Users;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private Double totalAmount;
    private Double advance;
    private Double balance;

    @Column(columnDefinition = "DATE") // Ensure only date is stored
    private LocalDate date;

    // One-to-Many relationship with BillItem
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> billItems;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Users user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Bill(Double totalAmount, Double advance, Double balance, LocalDate date) {
        this.totalAmount = totalAmount;
        this.advance = advance;
        this.balance = balance;
        this.date = date;
    }

    public Bill() {}

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = this.billItems;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }



}