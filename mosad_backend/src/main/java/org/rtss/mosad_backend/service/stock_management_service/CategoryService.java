package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.CategoryDTOMapper;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    private final CategoryDTOMapper categoryDTOMapper;

    public CategoryService(CategoryRepo categoryRepo, CategoryDTOMapper categoryDTOMapper) {
        this.categoryRepo = categoryRepo;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream().map(category -> new CategoryDTO(category.getCategoryId(), category.getCategoryName())).toList();
    }

    //Add category
    public CategoryDTO addCategory(CategoryDTO category) {

        return categoryDTOMapper.toDTO(categoryRepo.save(categoryDTOMapper.toEntity(category)));
    }

    //Update category
    public ResponseDTO updateCategory(CategoryDTO category) {

        CategoryDTO categoryDTO = categoryDTOMapper.toDTO(categoryRepo.save(categoryDTOMapper.toEntity(category)));

        return new ResponseDTO(true,"Category updated successfully "+categoryDTO);
    }

    //Delete category
    public ResponseDTO deleteCategory(Long categoryId) {
        categoryRepo.deleteById(categoryId);
        return new ResponseDTO(true,"Category deleted successfully");
    }




}
