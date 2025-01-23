package org.rtss.mosad_backend.dto.stock_management_dto;


public class AddItemDTO {

    private ItemDTO itemDTO;
    private ItemTyreDTO itemTyreDTO;
    private ItemBatteryDTO itemBatteryDTO;
    private ItemBranchDTO itemBranchDTO;

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

    public ItemBatteryDTO getItemBatteryDTO() {
        return itemBatteryDTO;
    }

    public void setItemBatteryDTO(ItemBatteryDTO itemBatteryDTO) {
        this.itemBatteryDTO = itemBatteryDTO;
    }
}
