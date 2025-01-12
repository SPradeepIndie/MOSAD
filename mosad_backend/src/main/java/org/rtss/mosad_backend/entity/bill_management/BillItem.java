package org.rtss.mosad_backend.entity.bill_management;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;

@Entity
@Table(name = "bill_items")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="item_id",referencedColumnName = "itemId")
    private Item item;
    private Integer quantity;
    private Double unitPrice;


    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    public BillItem() {
    }

    public BillItem(Item item, Integer quantity, Double unitPrice, Bill bill) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.bill = bill;
    }

    // Getters and Setters

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }


    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "BillItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", bill=" + bill +
                '}';
    }
}

