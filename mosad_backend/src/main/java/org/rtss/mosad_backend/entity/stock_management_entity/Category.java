package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false,unique = true)
    private Long categoryId;
    @Column(name = "categoryName", nullable = false,unique = true)
    private String categoryName;

    @ManyToMany
    @JoinTable(
            name = "category_brand",
            joinColumns = @JoinColumn(name = "categoryId"),
            inverseJoinColumns = @JoinColumn(name = "brandId")
    )
    private Set<Brand> brands;

    @OneToMany(mappedBy = "category")
    private Set<Item> items;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(Long categoryId, String categoryName, Set<Brand> brands, Set<Item> items) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brands = brands;
        this.items = items;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}

