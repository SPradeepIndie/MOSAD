package org.rtss.mosad_backend.dto;

public class NotificationDTO {
    private String msg;
    private String type;

    public NotificationDTO() {}

    public NotificationDTO(String type, String msg) {
        this.msg=msg;
        this.type=type;
    }

    // Getters & Setters
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
