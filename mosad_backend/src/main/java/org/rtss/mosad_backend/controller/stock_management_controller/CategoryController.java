package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.service.stock_management_service.CategoryService;
import org.rtss.mosad_backend.validator.ValidateHtmlPathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ValidateHtmlPathVariable validateHtmlPathVariable;

    public CategoryController(CategoryService categoryService, ValidateHtmlPathVariable validateHtmlPathVariable) {
        this.categoryService = categoryService;
        this.validateHtmlPathVariable = validateHtmlPathVariable;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.addCategory(categoryDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateCategory(@RequestParam String oldCatName, @RequestBody CategoryDTO categoryDTO) {
        String escapedOldCatName = validateHtmlPathVariable.escapeHTMLSpecialCharacters(oldCatName);
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO,escapedOldCatName));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteCategory(@RequestParam String catName) {
        String escapedCatName = validateHtmlPathVariable.escapeHTMLSpecialCharacters(catName);
        return ResponseEntity.ok(categoryService.deleteCategory(escapedCatName));
    }
}
