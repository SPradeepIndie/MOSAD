package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AddBrandDTO {

    @NotNull(message = "Brand can not be null")
    private BrandDTO brandDTO;

    @NotNull(message = "Category can not be nul")
    private CategoryDTO category;

    public AddBrandDTO() {
    }

    public AddBrandDTO(BrandDTO brandDTO, CategoryDTO category) {
        this.brandDTO = brandDTO;
        this.category = category;
    }

    public @NotNull(message = "Brand can not be null") BrandDTO getBrandDTO() {
        return brandDTO;
    }

    public void setBrandDTO(@NotNull(message = "Brand can not be null") BrandDTO brandDTO) {
        this.brandDTO = brandDTO;
    }

    public @NotNull(message = "Category can not be nul") CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Category can not be nul") CategoryDTO category) {
        this.category = category;
    }
}
