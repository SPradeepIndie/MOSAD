package org.rtss.mosad_backend.dto.stock_management_dto;

public class ItemTubeDTO {

    private Integer itemID;
    private String tubeSize;
    private String tubeCount;
    private double officialSellingPrice;
    private String vehicleType;

    public ItemTubeDTO() {
    }

    public ItemTubeDTO(Integer itemID, String tubeSize, String tubeCount, double officialSellingPrice, String vehicleType) {
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

    public String getTubeCount() {
        return tubeCount;
    }

    public void setTubeCount(String tubeCount) {
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
        return "ItemTubeDTO{" +
                "itemID=" + itemID +
                ", tubeSize='" + tubeSize + '\'' +
                ", tubeCount='" + tubeCount + '\'' +
                ", officialSellingPrice=" + officialSellingPrice +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
