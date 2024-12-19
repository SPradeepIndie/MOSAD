package org.rtss.mosad_backend.dto.stock_management_dto;




public class ItemBatteryDTO {
    private Integer itemID;
    private String batteryType;
    private Integer batteryCount;
    private double officialSellingPrice;
    private String vehicleType;

    public ItemBatteryDTO() {
    }

    public ItemBatteryDTO(Integer itemID, String batteryType, Integer batteryCount, double officialSellingPrice, String vehicleType) {
        this.itemID = itemID;
        this.batteryType = batteryType;
        this.batteryCount = batteryCount;
        this.officialSellingPrice = officialSellingPrice;
        this.vehicleType = vehicleType;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public Integer getBatteryCount() {
        return batteryCount;
    }

    public void setBatteryCount(Integer batteryCount) {
        this.batteryCount = batteryCount;
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
        return "ItemBatteryDTO{" +
                "itemID=" + itemID +
                ", batteryType='" + batteryType + '\'' +
                ", batteryCount='" + batteryCount + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
