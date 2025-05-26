package org.rtss.mosad_backend.dto;

import java.util.List;

public class HomeStatsDTO {

    private int totalCategories;
    private int totalBrands;
    private int totalItems;
    private int totalBillsToday;
    private List<BillCountDTO> past7DaysBillCount;  // New field for the 7 days bill count

    public HomeStatsDTO() {}

    public HomeStatsDTO(int totalCategories, int totalBrands, int totalItems, int totalBillsToday, List<BillCountDTO> past7DaysBillCount) {
        this.totalCategories = totalCategories;
        this.totalBrands = totalBrands;
        this.totalItems = totalItems;
        this.totalBillsToday = totalBillsToday;
        this.past7DaysBillCount = past7DaysBillCount;  // Initialize the past 7 days bill count
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public int getTotalBrands() {
        return totalBrands;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalBillsToday() {
        return totalBillsToday;
    }

    public List<BillCountDTO> getPast7DaysBillCount() {
        return past7DaysBillCount;
    }

    public void setPast7DaysBillCount(List<BillCountDTO> past7DaysBillCount) {
        this.past7DaysBillCount = past7DaysBillCount;
    }
}
