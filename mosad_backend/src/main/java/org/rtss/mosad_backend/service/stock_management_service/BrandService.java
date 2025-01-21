package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.validation.constraints.NotNull;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddBrandDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.BrandDTOMapper;
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

    //Adding brand
    public ResponseDTO addBrand(AddBrandDTO addBrandDto) {
        validateBrandDto(addBrandDto);

        // Check if the brand already exists
        Optional<Brand> existingBrandOpt = brandRepo.findByBrandName(addBrandDto.getBrandDTO().getBrandName());

        Set<Category> validCategories = validCategories(addBrandDto.getCategories());

        try {
            if (existingBrandOpt.isPresent()) {
                // Update categories for the existing brand
                Brand existingBrand = existingBrandOpt.get();
                updateExistingBrandCategories(existingBrand, validCategories);
                return new ResponseDTO(true, "Successfully updated brand: " + existingBrand.getBrandName());
            } else {
                // Create a new brand
                return createNewBrand(addBrandDto, validCategories);
            }
        } catch (ObjectNotValidException e) {
            return new ResponseDTO(false, "Brand addition failed: " + e.getMessage());
        }
    }
    
    //This method will only update the brand name
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
    public List<BrandDTO> getAllBrands(String catName) {
        Category category=categoryRepo.findCategoryByCategoryName(catName).orElseThrow(
                ()-> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Given category name is not valid")
        );
        List<Brand> brands =brandRepo.findAll().stream()
                .filter(brand -> brand.getCategories().contains(category))
                .toList();
        return brands.stream()
                .map(brandDTOMapper::BrandToBrandDto)
                .toList();

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
    private Set<Category> validCategories(@NotNull(message = "Category can not be nul") Set<CategoryDTO> categoryDtos){
        Set<Category> categories = new HashSet<>();
        for(CategoryDTO categoryDto: categoryDtos){
            Category oldCategory=categoryRepo.findCategoryByCategoryName(categoryDto.getCategoryName()).orElseThrow(
                    ()->new ObjectNotValidException(new HashSet<>(List.of("Can not find category called: "+categoryDto.getCategoryName(),"Before add a brand add the category first")))
            );
            categories.add(oldCategory);
        }
        return categories;
    }

    //Only update the categories for the existing brand
    private void updateExistingBrandCategories(Brand existingBrand, Set<Category> validCategories) {
        Set<Category> existingCategories = existingBrand.getCategories();

        // Add new categories to the existing ones
        validCategories.forEach(category -> {
            if (!existingCategories.contains(category)) {
                existingCategories.add(category); // Add only if it's not already present
            }
            category.setBrands(new HashSet<>(List.of(existingBrand))); // Update category's brands
        });

        // Update the existing brand with new categories
        existingBrand.setCategories(existingCategories);
        brandRepo.saveAndFlush(existingBrand); // Save updated brand
    }

    //Only add a new brand to the system
    private ResponseDTO createNewBrand(AddBrandDTO addBrandDto, Set<Category> validCategories) {
        Brand newBrand = brandDTOMapper.BrandDtoToBrand(addBrandDto.getBrandDTO());

        validCategories.forEach(category -> category.setBrands(new HashSet<>(List.of(newBrand))));

        newBrand.setCategories(validCategories);
        brandRepo.saveAndFlush(newBrand);

        return new ResponseDTO(true, "Successfully added new brand: " + newBrand.getBrandName());
    }

}

