package org.rtss.mosad_backend.dto;

public class ItemDataDTO {
    private String categoryName;
    private int totalBrands;
    private int totalItems;

    public ItemDataDTO(String categoryName, int totalBrands, int totalItems) {
        this.categoryName = categoryName;
        this.totalBrands = totalBrands;
        this.totalItems = totalItems;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalBrands() {
        return totalBrands;
    }

    public void setTotalBrands(int totalBrands) {
        this.totalBrands = totalBrands;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
