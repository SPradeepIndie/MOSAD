package org.rtss.mosad_backend.dto.bill_dtos;

public class BillResponeDTO {
    private Long billId;
    private Long customerId;
    private Long userId;

    public BillResponeDTO(Long billId, Long customerId, Long userId) {
        this.billId = billId;
        this.customerId = customerId;
        this.userId = userId;
    }

    public BillResponeDTO() {

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
