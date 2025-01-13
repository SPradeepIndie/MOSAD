package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandId", nullable = false,unique = true)
    private Long brandId;
    @Column(name = "brandName" , nullable = false,unique = true)
    private String brandName;

    @ManyToMany(mappedBy = "brands")
    private Set<Category> categories;

    @OneToMany(mappedBy = "brand")
    private Set<Item> items;

    public Brand() {
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public Brand(Long brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public Brand(Long brandId, String brandName, Set<Category> categories, Set<Item> items) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.categories = categories;
        this.items = items;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
