package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDto;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandDTOMapper {
    private final ModelMapper modelMapper;

    public BrandDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BrandDto BrandToBrandDto(Brand brand) {
        if(brand == null) {
            throw new IllegalArgumentException("Brand cannot be null");
        }
        return modelMapper.map(brand, BrandDto.class);
    }

    public Brand BrandDtoToBrand(BrandDto brandDto) {
        if(brandDto == null) {
            throw new IllegalArgumentException("BrandDTO cannot be null");
        }
        return modelMapper.map(brandDto, Brand.class);
    }
}
