package org.rtss.mosad_backend.dto.stock_management_dto;

public class ItemOtherAccessoriesDTO {

    private Integer itemID;
    private String accessoryType;
    private Integer accessoryCount;
    private double officialSellingPrice;
    private String vehicleType;

    public ItemOtherAccessoriesDTO() {
    }

    public ItemOtherAccessoriesDTO(Integer itemID, String accessoryType, Integer accessoryCount, double officialSellingPrice, String vehicleType) {
        this.itemID = itemID;
        this.accessoryType = accessoryType;
        this.accessoryCount = accessoryCount;
        this.officialSellingPrice = officialSellingPrice;
        this.vehicleType = vehicleType;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public Integer getAccessoryCount() {
        return accessoryCount;
    }

    public void setAccessoryCount(Integer accessoryCount) {
        this.accessoryCount = accessoryCount;
    }

    public double getOfficialSellingPrice() {
        return officialSellingPrice;
    }

    public void setOfficialSellingPrice(double officialSellingPrice) {
        this.officialSellingPrice = officialSellingPrice;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "ItemOtherAccessoriesDTO{" +
                "itemID=" + itemID +
                ", accessoryType='" + accessoryType + '\'' +
                ", accessoryCount='" + accessoryCount + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
