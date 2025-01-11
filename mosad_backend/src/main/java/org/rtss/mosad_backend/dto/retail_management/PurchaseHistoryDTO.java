package org.rtss.mosad_backend.dto.retail_management;


import java.util.Date;

public class PurchaseHistoryDTO {


    private Date date;
    private String productName;
    private Integer quantity;
    private Double price;

    public PurchaseHistoryDTO(Date date, String productName, Integer quantity, Double price) {
        this.date = date;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public PurchaseHistoryDTO() {
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
