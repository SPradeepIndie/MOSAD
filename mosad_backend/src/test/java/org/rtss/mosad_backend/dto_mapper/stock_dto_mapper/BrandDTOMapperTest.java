package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BrandDTOMapperTest {

    private BrandDTOMapper brandDTOMapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper=new ModelMapper();
        brandDTOMapper=new BrandDTOMapper(modelMapper);
    }


    @Test
    void shouldMapBrandToBrandDTO() {
        //Given
        Brand brand = new Brand(
                1233423423L,
                "Brand1",
                new HashSet<Category>(List.of(new Category("my category"))),
                new HashSet<Item>(List.of(new Item()))
        );

        //When
        BrandDTO brandDto=brandDTOMapper.BrandToBrandDto(brand);

        //Then
        assertNotNull(brandDto);
        assertInstanceOf(BrandDTO.class, brandDto);
        assertEquals(brandDto.getBrandName(), brand.getBrandName());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfBrandIsNull() {
        //Given nothing -> When & Then
        assertThrows(IllegalArgumentException.class,
                () -> brandDTOMapper.BrandToBrandDto(null),
                "Brand cannot be null");
    }

        @Test
        void shouldMapBrandDTOToBrand() {
            //Given
            BrandDTO brandDTO = new BrandDTO(
                    "Brand1"
            );

            //When
            Brand brand=brandDTOMapper.BrandDtoToBrand(brandDTO);

            //Then
            assertNotNull(brand);
            assertInstanceOf(Brand.class, brand);
            assertEquals(brand.getBrandName(), brandDTO.getBrandName());
        }

    @Test
    void shouldThrowIllegalArgumentExceptionIfBrandDTOIsNull() {
        //Given nothing -> When & Then
        assertThrows(IllegalArgumentException.class,
                () -> brandDTOMapper.BrandDtoToBrand(null),
                "BrandDTO cannot be null");
    }


}