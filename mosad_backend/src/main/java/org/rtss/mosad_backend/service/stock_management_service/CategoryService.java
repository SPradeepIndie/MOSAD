package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.CategoryDTOMapper;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final DtoValidator dtoValidator;
    private final CategoryDTOMapper categoryDTOMapper;

    public CategoryService(CategoryRepo categoryRepo, DtoValidator dtoValidator, CategoryDTOMapper categoryDTOMapper) {
        this.categoryRepo = categoryRepo;
        this.dtoValidator = dtoValidator;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream().map(categoryDTOMapper::categoryToDTO).toList();
    }

    //Add category
    public CategoryDTO addCategory(CategoryDTO category) {
        dtoValidator.validate(category);
        return categoryDTOMapper.categoryToDTO(categoryRepo.save(categoryDTOMapper.categoryDtoToEntity(category)));
    }

    //Update category
    public ResponseDTO updateCategory(CategoryDTO category,String oldCatName) {
        dtoValidator.validate(category);
        Category oldCategory= categoryRepo.findCategoryByCategoryName(oldCatName).orElseThrow(
                ()->new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Old category name is mandatory")
        );
        Category newCategory= categoryDTOMapper.categoryDtoToEntity(category);
        newCategory.setCategoryId(oldCategory.getCategoryId());
        newCategory.setBrands(oldCategory.getBrands());
        categoryRepo.save(newCategory);
        return new ResponseDTO(true,"Category updated successfully ");
    }

    //Delete category
    public ResponseDTO deleteCategory(String catName) {
        Category oldCategory= categoryRepo.findCategoryByCategoryName(catName).orElseThrow(
                ()->new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Couldn't find category name called: "+catName)
        );
        categoryRepo.delete(oldCategory);
        return new ResponseDTO(true,"Category deleted successfully");
    }




}
