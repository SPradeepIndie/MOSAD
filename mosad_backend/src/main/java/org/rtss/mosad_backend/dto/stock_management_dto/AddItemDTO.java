package org.rtss.mosad_backend.dto.stock_management_dto;


public class AddItemDTO {

    private ItemDTO itemDTO;
    private ItemTyreDTO itemTyreDTO;
    private ItemBranchDTO itemBranchDTO;

    public AddItemDTO(){}

    public AddItemDTO(ItemDTO itemDTO, ItemTyreDTO itemTyreDTO, ItemBranchDTO itemBranchDTO) {
        this.itemDTO = itemDTO;
        this.itemTyreDTO = itemTyreDTO;
        this.itemBranchDTO = itemBranchDTO;
    }

    public AddItemDTO(ItemDTO itemDTO, ItemBranchDTO itemBranchDTO) {
        this.itemDTO = itemDTO;
        this.itemBranchDTO = itemBranchDTO;
    }

    // Getters and Setters
    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public ItemTyreDTO getItemTyreDTO() {
        return itemTyreDTO;
    }

    public void setItemTyreDTO(ItemTyreDTO itemTyreDTO) {
        this.itemTyreDTO = itemTyreDTO;
    }

    public ItemBranchDTO getItemBranchDTO() {
        return itemBranchDTO;
    }

    public void setItemBranchDTO(ItemBranchDTO itemBranchDTO) {
        this.itemBranchDTO = itemBranchDTO;
    }

}
