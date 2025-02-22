package org.rtss.mosad_backend.entity.bill_management;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;

@Entity
@Table(name = "bill_items")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billItemId;
    private String description;
    private Integer quantity;
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;


    public BillItem(String description, Integer quantity, Double unitPrice, Bill bill,Item item) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.bill = bill;
        this.item=item;
    }

    public BillItem() {

    }

    // Getters and Setters

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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


    public Long getBillItemId() {
        return billItemId;
    }

    public void setBillItemId(Long billItemId) {
        this.billItemId = billItemId;
    }

    @Override
    public String toString() {
        return "BillItem{" +
                "id=" + billItemId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", bill=" + bill +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}