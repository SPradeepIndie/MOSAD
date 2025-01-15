package org.rtss.mosad_backend.dto.stock_management_dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AddBrandDTO {

    @NotNull(message = "Brand can not be null")
    private BrandDTO brandDTO;

    @NotNull(message = "Category can not be nul")
    private Set<CategoryDTO> categories;

    public AddBrandDTO() {
    }

    public AddBrandDTO(BrandDTO brandDTO, Set<CategoryDTO> categories) {
        this.brandDTO = brandDTO;
        this.categories = categories;
    }

    public @NotNull(message = "Brand can not be null") BrandDTO getBrandDTO() {
        return brandDTO;
    }

    public void setBrandDTO(@NotNull(message = "Brand can not be null") BrandDTO brandDTO) {
        this.brandDTO = brandDTO;
    }

    public @NotNull(message = "Category can not be nul") Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(@NotNull(message = "Category can not be nul") Set<CategoryDTO> categories) {
        this.categories = categories;
    }
}
