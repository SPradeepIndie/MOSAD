package org.rtss.mosad_backend.dto.bill_dtos;

import org.rtss.mosad_backend.entity.customer.Customer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class BillDTO {
    private Long id; // Unique identifier for the bill
    private String customerName; // Customer name
    private Double advance;
    private Double total;
    private Double balance;
    private LocalDate date; // Date of the bill
    private List<BillItemDTO> items; // List of bill items

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getTotalAmount() {
        return total;
    }

    public void setTotalAmount(Double total) {
        this.total = total;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    // Getter for date (returns java.util.Date)
    public Date getDate() {
        if (date == null) {
            return null; // Handle null case
        }
        // Convert LocalDate to java.util.Date
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Setter for date (accepts java.util.Date)
    public void setDate(Date date) {
        if (date != null) {
            // Convert java.util.Date to LocalDate
            this.date = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            this.date = null;
        }
    }
}
