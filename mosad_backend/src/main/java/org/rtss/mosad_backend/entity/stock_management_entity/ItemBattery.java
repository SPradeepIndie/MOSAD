package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class ItemBattery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemID;
    private String batteryType;
    private Integer batteryCount;
    private double officialSellingPrice;
    private String vehicleType;

    public ItemBattery() {
    }

    public ItemBattery(String batteryType, Integer itemID, Integer batteryCount, double officialSellingPrice, String vehicleType) {
        this.batteryType = batteryType;
        this.itemID = itemID;
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
        return "ItemBattery{" +
                "batteryCount='" + batteryCount + '\'' +
                ", itemID=" + itemID +
                ", batteryType='" + batteryType + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
