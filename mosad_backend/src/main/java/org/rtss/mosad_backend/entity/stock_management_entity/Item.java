package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId",nullable = false,unique = true)
    private Long itemId;
    @Column(name = "itemName")
    private String itemName;
    @Column(name = "itemDescription")
    private String itemDescription;
    @Column(name = "companyPrice")
    private double companyPrice;
    @Column(name = "reatailPrice")
    private double retailPrice;
    @Column(name = "discount")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ItemBranch> itemBranches;




    public Item() {
    }

    public Item(String itemName, String itemDescription, double companyPrice, double retailPrice, double discount, Category category, Brand brand, Set<ItemBranch> itemBranches) {

        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.companyPrice = companyPrice;
        this.retailPrice = retailPrice;
        this.discount = discount;
        this.category = category;
        this.brand = brand;
        this.itemBranches = itemBranches;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Set<ItemBranch> getItemBranches() {
        return itemBranches;
    }

    public void setItemBranches(Set<ItemBranch> itemBranches) {
        this.itemBranches = itemBranches;
    }

}
