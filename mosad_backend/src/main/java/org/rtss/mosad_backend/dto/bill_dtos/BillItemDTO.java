package org.rtss.mosad_backend.dto.bill_dtos;

public class BillItemDTO {
    private Long itemid; // Unique identifier for the bill item
    private String description;
    private Double unitPrice;
    private Integer quantity;
    private Double subtotal;

    // Getters and Setters
    public Long getItemId() {
        return itemid;
    }

    public void setItemId(Long itemid) {
        this.itemid = itemid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}

