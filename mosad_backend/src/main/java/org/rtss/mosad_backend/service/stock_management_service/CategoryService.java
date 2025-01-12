package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream().map(category -> new CategoryDTO(category.getCategoryId(), category.getCategoryName())).toList();
    }
}
