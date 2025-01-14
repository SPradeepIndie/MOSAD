package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    private Long categoryId;
    @NotBlank(message = "Category name can not be empty")
    private String categoryName;

    public CategoryDTO() {}

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryDTO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}