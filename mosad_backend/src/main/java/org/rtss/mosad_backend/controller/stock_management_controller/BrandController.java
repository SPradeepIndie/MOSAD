package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddBrandDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.service.stock_management_service.BrandService;
import org.rtss.mosad_backend.validator.ValidateHtmlPathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/brand")
public class BrandController {

    private final BrandService brandService;
    private final ValidateHtmlPathVariable validateHtmlPathVariable;

    public BrandController(BrandService brandService, ValidateHtmlPathVariable validateHtmlPathVariable) {
        this.brandService = brandService;
        this.validateHtmlPathVariable = validateHtmlPathVariable;
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> viewAllBrands(@RequestParam String catName) {
        String escapedCategoryName= validateHtmlPathVariable.escapeHTMLspecailCharaters(catName);
        return ResponseEntity.ok(brandService.getAllBrands(escapedCategoryName));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateBrand(@RequestBody BrandDTO brandDto) {
        return ResponseEntity.ok(brandService.updateBrand(brandDto));
    }
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteBrand(@RequestBody AddBrandDTO addBrandDto) {
        return ResponseEntity.ok(brandService.deleteBrand(addBrandDto));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addBrand(@RequestBody AddBrandDTO addBrandDto) {
        return ResponseEntity.ok(brandService.addBrand(addBrandDto));
    }
}
