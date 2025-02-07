package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItemTyreDTO {


    @NotBlank(message = "Tyre size cannot be blank")
    @Size(max = 50, message = "Tyre size cannot exceed 50 characters")
    public String tyreSize;

    @NotBlank(message = "Pattern cannot be blank")
    @Size(max = 100, message = "Pattern cannot exceed 100 characters")
    public String pattern;

    @NotBlank(message = "Vehicle type cannot be blank")
    @Size(max = 50, message = "Vehicle type cannot exceed 50 characters")
    public String vehicleType;


    // Getters and Setters

    public String getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(String tyreSize) {
        this.tyreSize = tyreSize;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}