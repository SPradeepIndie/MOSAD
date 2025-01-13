package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDto;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.BrandDTOMapper;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.repository.stock_management_repository.BrandRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepo brandRepo;
    private final BrandDTOMapper brandDTOMapper;

    public BrandService(BrandRepo brandRepo, BrandDTOMapper brandDTOMapper) {
        this.brandRepo = brandRepo;
        this.brandDTOMapper = brandDTOMapper;
    }

    public ResponseDTO addOrUpdateBrand(BrandDto brandDto,String cat) {
        Brand newBrand = brandDTOMapper.BrandDtoToBrand(brandDto);
        Optional<Brand> brands= brandRepo.findByBrandName(newBrand.getBrandName());
        if(brands.isPresent()) {
            Brand oldBrand = brands.get();
            oldBrand.setBrandName(newBrand.getBrandName());
            brandRepo.save(oldBrand);
            return new ResponseDTO(true,"Successfully updated brand"+oldBrand.getBrandName());
        }
        brandRepo.save(newBrand);
        return new ResponseDTO(true,"Successfully add new brand"+newBrand.getBrandName());
    }

    public ResponseDTO deleteBrand(BrandDto brandDto,String cat) {
        Brand brand= brandRepo.findByBrandName(brandDto.getBrandName()).orElseThrow(
                () -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Couldn't find given brand"+brandDto.getBrandName())
        );
        brandRepo.delete(brand);
        return new ResponseDTO(true,"Successfully deleted brand"+brandDto.getBrandName());
    }
    public List<BrandDto> getAllBrands() {
        List<BrandDto> brandDtos=new ArrayList<>();
        for (Brand brand : brandRepo.findAll()) {
            brandDtos.add(brandDTOMapper.BrandToBrandDto(brand));
        }
        return brandDtos;

    }
}
