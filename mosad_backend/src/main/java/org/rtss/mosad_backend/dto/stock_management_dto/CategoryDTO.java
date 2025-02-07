package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

    @NotBlank(message = "Category name can not be empty")
    @Size(max = 15,message = "Category name should not be exceed 15 characters")
    private String categoryName;

    public CategoryDTO() {}

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}