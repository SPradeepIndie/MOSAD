package org.rtss.mosad_backend.dto;

import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.springframework.stereotype.Component;

@Component
public class ResponseDTO {
    private boolean success;
    private String message;

    public ResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseDTO() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public void setData(Bill savedBill) {

    }
}
