package org.rtss.mosad_backend.dto.stock_management_dto;

public class ItemBranchDTO {
    private Long branchId;
    private Integer availableQuantity;

    public ItemBranchDTO() {}

    public ItemBranchDTO(Long branchId, Integer availableQuantity) {
        this.branchId = branchId;
        this.availableQuantity = availableQuantity;
    }

    public ItemBranchDTO(Integer availableQuantity) {
    }

    // Getters and Setters
    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
