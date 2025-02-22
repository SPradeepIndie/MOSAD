package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BrandDTO {

    @NotBlank(message = "Brand name is mandatory")
    @Size(max = 30,message = "Brand name shouldn't be exceed 30 characters")
    private String brandName;

    public BrandDTO() {
    }
    public BrandDTO(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
