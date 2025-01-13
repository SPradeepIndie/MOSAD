package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class BrandDto {

    @NotBlank(message = "Brand name is mandatory")
    @Size(max = 30,message = "Brand name shouldn't be exceed 30 characters")
    private String brandName;

    public BrandDto() {
    }

    public BrandDto(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
