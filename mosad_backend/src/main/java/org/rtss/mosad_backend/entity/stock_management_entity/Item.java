package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.branch_management.Branch;

import java.util.Set;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId",nullable = false,unique = true)
    private Long itemId;
    @Column(name = "availableQuantity")
    private Integer availableQuantity;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "item_branch",
            joinColumns = @JoinColumn(name = "itemId"),
            inverseJoinColumns = @JoinColumn(name = "branchId")
    )
    private Set<Branch> branches;

    //TODO Create Many to many relation ship with Bill
    //TODO Create all args constructor

    public Item() {
    }

    public Item(Integer availableQuantity, String itemName, String itemDescription, double companyPrice, double retailPrice, double discount, Category category, Brand brand, Set<Branch> branches) {
        this.availableQuantity = availableQuantity;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.companyPrice = companyPrice;
        this.retailPrice = retailPrice;
        this.discount = discount;
        this.category = category;
        this.brand = brand;
        this.branches = branches;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
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

    public Set<Branch> getBranches() {
        return branches;
    }

    public void setBranches(Set<Branch> branches) {
        this.branches = branches;
    }

    public void setSize(String size) {
    }
}
