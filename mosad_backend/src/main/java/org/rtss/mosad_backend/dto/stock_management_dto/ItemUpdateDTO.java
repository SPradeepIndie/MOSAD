package org.rtss.mosad_backend.dto.stock_management_dto;

import org.rtss.mosad_backend.entity.stock_management_entity.Brand;

public class ItemUpdateDTO {

    private String size;

    private String brand;
    private String name;
    private int quantity;
    private double price;

    // Default constructor
    public ItemUpdateDTO() {}

    // Parameterized constructor
    public ItemUpdateDTO(String name, int quantity, double price, String brand, String size) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
