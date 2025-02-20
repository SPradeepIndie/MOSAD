package org.rtss.mosad_backend.dto.bill_dtos;

public class BillResponeDTO {
    private Long billId;
    private Long customerId;

    public BillResponeDTO(Long billId, Long customerId) {
        this.billId = billId;
        this.customerId = customerId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
