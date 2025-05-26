package org.rtss.mosad_backend.dto;

import java.time.LocalDate;

public class BillCountDTO {

    private LocalDate billDate;
    private long billsCount;

    public BillCountDTO(LocalDate billDate, long billsCount) {
        this.billDate = billDate;
        this.billsCount = billsCount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public long getBillsCount() {
        return billsCount;
    }

    public void setBillsCount(long billsCount) {
        this.billsCount = billsCount;
    }

    @Override
    public String toString() {
        return "BillCountDTO{" +
                "billDate=" + billDate +
                ", billsCount=" + billsCount +
                '}';
    }
}
