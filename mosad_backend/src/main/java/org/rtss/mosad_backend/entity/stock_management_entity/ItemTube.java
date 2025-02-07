package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class ItemTube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemID;
    private String tubeSize;
    private Integer tubeCount;
    private double officialSellingPrice;
    private String vehicleType;

    public ItemTube() {
    }

    public ItemTube(Integer itemID, String tubeSize, Integer tubeCount, double officialSellingPrice, String vehicleType) {
        this.itemID = itemID;
        this.tubeSize = tubeSize;
        this.tubeCount = tubeCount;
        this.officialSellingPrice = officialSellingPrice;
        this.vehicleType = vehicleType;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getTubeSize() {
        return tubeSize;
    }

    public void setTubeSize(String tubeSize) {
        this.tubeSize = tubeSize;
    }

    public Integer getTubeCount() {
        return tubeCount;
    }

    public void setTubeCount(Integer tubeCount) {
        this.tubeCount = tubeCount;
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
        return "ItemTube{" +
                "itemID=" + itemID +
                ", tubeSize='" + tubeSize + '\'' +
                ", tubeCount='" + tubeCount + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
