package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddBrandDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDTO;
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
    @Transactional
    public ResponseDTO addBrand(AddBrandDTO addBrandDto) {
        validateDtoS(addBrandDto);
        //check category is in db
        Category category=checkRegisteredCategory(addBrandDto.getCategory().getCategoryName());

        // Check if the brand already exists
        Optional<Brand> existingBrandOpt = brandRepo.findByBrandName(addBrandDto.getBrandDTO().getBrandName());

        try {
            if (existingBrandOpt.isPresent()) {
                Brand existingBrand = existingBrandOpt.get();
                updateExistingBrandCategories(existingBrand,category);
                return new ResponseDTO(true, "Successfully updated brand: " + existingBrand.getBrandName());
            } else {
                // Create a new brand
                Brand newBrand=brandDTOMapper.BrandDtoToBrand(addBrandDto.getBrandDTO());
                createNewBrand(newBrand,category);
                return new ResponseDTO(true, "Successfully added new brand: " + newBrand.getBrandName());
            }
        } catch (ObjectNotValidException e) {
            return new ResponseDTO(false, "Brand addition failed: " + e.getMessage());
        }
    }

    //This method will only update the brand name
    @Transactional
    public ResponseDTO updateBrandName(String newBrandName,String oldBrandName) {
        Brand oldBrand= checkRegisteredBrand(oldBrandName);
        oldBrand.setBrandName(newBrandName);
        brandRepo.save(oldBrand);
        return new ResponseDTO(true,"Successfully updated brand"+oldBrand.getBrandName());
    }

    //delete the brand
    @Transactional
    public ResponseDTO deleteBrand(String brandName) {
        Brand brand= checkRegisteredBrand(brandName);

        // Remove associations from categories
        for (Category category : brand.getCategories()) {
            category.getBrands().remove(brand); // Remove this brand from each category
            categoryRepo.save(category); // Save updated category
        }

        brandRepo.delete(brand);
        return new ResponseDTO(true,"Successfully deleted brand"+brandName);
    }

    //get all brands
    public List<BrandDTO> getAllBrands(String catName) {
        Category category=checkRegisteredCategory(catName);
        List<Brand> brands =brandRepo.findAll().stream()
                .filter(brand -> brand.getCategories().contains(category))
                .toList();
        return brands.stream()
                .map(brandDTOMapper::BrandToBrandDto)
                .toList();

    }


    //validate addBrandDTO
    private void validateDtoS(AddBrandDTO addBrandDto) {
        dtoValidator.validate(addBrandDto);
        dtoValidator.validate(addBrandDto.getBrandDTO());
        dtoValidator.validate(addBrandDto.getCategory());
    }

    //Check the category is registered
    private Category checkRegisteredCategory(String cat){
            return CategoryRepo.findCategoryByCategoryName(cat).orElseThrow(
                    ()->new ObjectNotValidException(new HashSet<>(List.of("Can not find category called: "+cat,"Before add a brand add the category first")))
            );
    }

    //Check the brand is registered
    private Brand checkRegisteredBrand(String brandName){
        return brandRepo.findByBrandName(brandName).orElseThrow(
                () -> new ObjectNotValidException(new HashSet<>(List.of("Can not find brand called: ",brandName)))
        );
    }

    //Only update the categories for the existing brand
    private void updateExistingBrandCategories(Brand existingBrand, Category category) {
        Set<Category> existingCategories = existingBrand.getCategories();

        // Add new category to the existing ones
        if(existingCategories.contains(category)){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Brand already exists");
        }

        category.getBrands().add(existingBrand);
        existingBrand.getCategories().add(category);
        //updating db
        brandRepo.saveAndFlush(existingBrand);

    }

    //Only add a new brand to the system
    private void createNewBrand(Brand newBrand, Category category) {
        category.getBrands().add(newBrand);
        newBrand.setCategories(new HashSet<>(List.of(category)));
        //updating db
        brandRepo.saveAndFlush(newBrand);

    }

}

