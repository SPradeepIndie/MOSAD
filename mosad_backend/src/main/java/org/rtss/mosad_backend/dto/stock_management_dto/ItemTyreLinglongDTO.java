package org.rtss.mosad_backend.dto.stock_management_dto;

public class ItemTyreLinglongDTO {
    private Integer itemID;
    private String tyreSize;
    private String pattern;
    private Integer tyreCount;
    private double officialSellingPrice;
    private String vehicleType;


    public ItemTyreLinglongDTO() {
    }

    public ItemTyreLinglongDTO(Integer itemID, String tyreSize, String pattern, Integer tyreCount, double officialSellingPrice, String vehicleType) {
        this.itemID = itemID;
        this.tyreSize = tyreSize;
        this.pattern = pattern;
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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
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
        return "ItemTyreLinglongDTO{" +
                "itemID=" + itemID +
                ", tyreSize='" + tyreSize + '\'' +
                ", pattern='" + pattern + '\'' +
                ", tyreCount='" + tyreCount + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}