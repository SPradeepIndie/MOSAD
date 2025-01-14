package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOMapper {
    private final ModelMapper modelMapper;

    public CategoryDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDTO toDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO,Category.class);
    }

}
