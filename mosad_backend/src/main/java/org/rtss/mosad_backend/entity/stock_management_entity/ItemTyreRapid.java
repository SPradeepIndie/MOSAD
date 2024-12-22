package org.rtss.mosad_backend.entity.stock_management_entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class ItemTyreRapid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemID;

    private String tyreSize;
    private Integer tyreCount;
    private double officialSellingPrice;
    private String vehicleType;


    public ItemTyreRapid() {
    }

    public ItemTyreRapid(Integer itemID, String tyreSize, Integer tyreCount, double officialSellingPrice, String vehicleType) {
        this.itemID = itemID;
        this.tyreSize = tyreSize;
        this.tyreCount = tyreCount;
        this.officialSellingPrice = officialSellingPrice;
        this.vehicleType = vehicleType;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(String tyreSize) {
        this.tyreSize = tyreSize;
    }

    public Integer getTyreCount() {
        return tyreCount;
    }

    public void setTyreCount(Integer tyreCount) {
        this.tyreCount = tyreCount;
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
        return "ItemTyreRapid{" +
                "itemID=" + itemID +
                ", tyreSize='" + tyreSize + '\'' +
                ", tyreCount='" + tyreCount + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
