package org.rtss.mosad_backend.dto;

public class HomeCalDTO {
    private String date;

    public HomeCalDTO(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
