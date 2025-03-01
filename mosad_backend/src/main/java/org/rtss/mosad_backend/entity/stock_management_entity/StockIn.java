package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.branch_management.Branch;

import java.time.LocalDate;

@Entity
public class StockIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne
    @JoinColumn(name="branchId")
    private Branch branch;

    private LocalDate date;
    private int quantity;




    public StockIn(){

    }


    public StockIn(Long id, Item item, Branch branch, LocalDate date,int quantity) {
        this.id = id;
        this.item = item;
        this.branch = branch;
        this.date = date;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
