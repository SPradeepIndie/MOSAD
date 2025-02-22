package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.*;

public class ItemDTO {
    private Long itemId;
    @NotBlank(message = "Item name cannot be blank")
    @Size(max = 100, message = "Item name cannot exceed 100 characters")
    private String itemName;

    @Size(max = 500, message = "Item description cannot exceed 500 characters")
    private String itemDescription;

    @Positive(message = "Company price must be greater than zero")
    private double companyPrice;

    @Positive(message = "Retail price must be greater than zero")
    private double retailPrice;

    @Min(value = 0, message = "Discount cannot be less than 0")
    @Max(value = 100, message = "Discount cannot be greater than 100")
    private double discount;

    @NotNull(message = "Category  cannot be null")
    private String category;

    @NotNull(message = "Brand  cannot be null")
    private String brand;

    public ItemDTO() {}

    public ItemDTO(Long itemId, String itemName, String itemDescription, double companyPrice, double retailPrice, double discount, String category, String brand) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.companyPrice = companyPrice;
        this.retailPrice = retailPrice;
        this.discount = discount;
        this.category = category;
        this.brand = brand;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}