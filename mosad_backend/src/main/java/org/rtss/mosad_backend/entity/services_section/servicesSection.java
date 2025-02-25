package org.rtss.mosad_backend.entity.services_section;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "services")
public class servicesSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long servicesId;

    @Column(columnDefinition = "DATE") // Ensure only date is stored
    private LocalDate servicesDate;

    private Double Amount;

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

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }
}
