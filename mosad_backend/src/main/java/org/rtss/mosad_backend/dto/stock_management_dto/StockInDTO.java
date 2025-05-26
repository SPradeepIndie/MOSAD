package org.rtss.mosad_backend.dto.stock_management_dto;

import java.time.LocalDate;

public class StockInDTO {
    private Long itemId;
    private Long branchId;
    private LocalDate date;
    private int quantity;
    private double officialSellingPrice;

    public StockInDTO() {
    }

    public StockInDTO(Long itemId, Long branchId, LocalDate date, int quantity, double officialSellingPrice) {
        this.itemId = itemId;
        this.branchId = branchId;
        this.date = date;
        this.quantity = quantity;
        this.officialSellingPrice = officialSellingPrice;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOfficialSellingPrice() {
        return officialSellingPrice;
    }

    public void setOfficialSellingPrice(double officialSellingPrice) {
        this.officialSellingPrice = officialSellingPrice;
    }
}
