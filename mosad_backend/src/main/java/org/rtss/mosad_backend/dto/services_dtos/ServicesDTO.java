package org.rtss.mosad_backend.dto.services_dtos;

import java.time.LocalDate;


public class ServicesDTO {
    private Long servicesId;
    private LocalDate servicesDate;

    public ServicesDTO() {
    }

    public ServicesDTO(Long servicesId, LocalDate servicesDate) {
        this.servicesId = servicesId;
        this.servicesDate = servicesDate;
    }

    public Long getServicesId() {
        return servicesId;
    }

    public void setServicesId(Long servicesId) {
        this.servicesId = servicesId;
    }

    public LocalDate getServicesDate() {
        return servicesDate;
    }

    public void setServicesDate(LocalDate servicesDate) {
        this.servicesDate = servicesDate;
    }
}
