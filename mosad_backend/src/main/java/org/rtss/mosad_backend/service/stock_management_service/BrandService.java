package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.validation.constraints.NotNull;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddBrandDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.BrandDTOMapper;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.CategoryDTOMapper;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.stock_management_repository.BrandRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.*;

@Service
public class BrandService {

    private final BrandRepo brandRepo;
    private final BrandDTOMapper brandDTOMapper;
    private final DtoValidator dtoValidator;
    private final CategoryRepo categoryRepo;

    public BrandService(BrandRepo brandRepo, BrandDTOMapper brandDTOMapper, DtoValidator dtoValidator, CategoryRepo categoryRepo) {
        this.brandRepo = brandRepo;
        this.brandDTOMapper = brandDTOMapper;
        this.dtoValidator = dtoValidator;
        this.categoryRepo = categoryRepo;
    }


    public ResponseDTO addBrand(AddBrandDTO addBrandDto){
        validateBrandDto(addBrandDto);
        Brand newBrand = brandDTOMapper.BrandDtoToBrand(addBrandDto.getBrandDTO());
        try {
            newBrand.setCategories(ValidCategories(addBrandDto.getCategories()));
            brandRepo.save(newBrand);
            return new ResponseDTO(true,"Successfully add new brand"+newBrand.getBrandName());
        } catch (ObjectNotValidException e) {
            return new ResponseDTO(false, "Brand addition failed: ");
        }
    }

    public ResponseDTO updateBrand(BrandDTO brandDto) {
        dtoValidator.validate(brandDto);
        Brand oldBrand= brandRepo.findByBrandName(brandDto.getBrandName()).orElseThrow(
                ()->new ObjectNotValidException(new HashSet<>(List.of("")))
        );
        Brand newBrand = brandDTOMapper.BrandDtoToBrand(brandDto);
        newBrand.setBrandId(oldBrand.getBrandId());
        newBrand.setItems(oldBrand.getItems());
        brandRepo.save(newBrand);
        return new ResponseDTO(true,"Successfully updated brand"+oldBrand.getBrandName());
    }

    //delete the brand
    public ResponseDTO deleteBrand(AddBrandDTO addBrandDto) {
        validateBrandDto(addBrandDto);
        Brand brand= brandRepo.findByBrandName(addBrandDto.getBrandDTO().getBrandName()).orElseThrow(
                () -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Couldn't find given brand"+addBrandDto.getBrandDTO().getBrandName())
        );
        brandRepo.delete(brand);
        return new ResponseDTO(true,"Successfully deleted brand"+addBrandDto.getBrandDTO().getBrandName());
    }

    //get all brands
    public List<Brand> getAllBrands(String catName) {
        Category category=categoryRepo.findCategoryByCategoryName(catName).orElseThrow(
                ()-> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Given category name is not valid")
        );
       // List<BrandDTO> brandDTOS =new ArrayList<>();
//        for (Brand brand : brandRepo.findAll()) {
//            brand.getCategories().
//            brandDTOS.add(brandDTOMapper.BrandToBrandDto(brand));
//        }

        return brandRepo.findAll();

    }

    //validate addBrandDTO
    private void validateBrandDto(AddBrandDTO addBrandDto) {
        dtoValidator.validate(addBrandDto);
        dtoValidator.validate(addBrandDto.getBrandDTO());
        for(CategoryDTO categoryDto: addBrandDto.getCategories()){
            dtoValidator.validate(categoryDto);
        }
    }

    //Check the categories are valid
    private Set<Category> ValidCategories(@NotNull(message = "Category can not be nul") Set<CategoryDTO> categoryDtos){
        Set<Category> categories = new HashSet<>();
        for(CategoryDTO categoryDto: categoryDtos){
            Category oldCategory=categoryRepo.findCategoryByCategoryName(categoryDto.getCategoryName()).orElseThrow(
                    ()->new ObjectNotValidException(new HashSet<>(List.of("Can no find category called: "+categoryDto.getCategoryName(),"Before add a brand add the category first")))
            );
            categories.add(oldCategory);
        }
        return categories;
    }
}

