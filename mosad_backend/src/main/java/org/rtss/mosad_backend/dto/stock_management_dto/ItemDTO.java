package org.rtss.mosad_backend.dto.stock_management_dto;

public class ItemDTO {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private double companyPrice;
    private double retailPrice;
    private double discount;
    private Long categoryId;
    private Long brandId;

    public ItemDTO() {}

    public ItemDTO(Long itemId, String itemName, String itemDescription, double companyPrice, double retailPrice, double discount, Long categoryId, Long brandId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.companyPrice = companyPrice;
        this.retailPrice = retailPrice;
        this.discount = discount;
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getCompanyPrice() {
        return companyPrice;
    }

    public void setCompanyPrice(double companyPrice) {
        this.companyPrice = companyPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
