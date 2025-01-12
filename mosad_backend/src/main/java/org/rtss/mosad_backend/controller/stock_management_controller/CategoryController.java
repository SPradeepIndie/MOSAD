package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.CategoryDTO;
import org.rtss.mosad_backend.service.stock_management_service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());

    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.addCategory(categoryDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.deleteCategory(categoryId));
    }
}
