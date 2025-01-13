package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.BrandDto;
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
    public ResponseEntity<List<BrandDto>> viewAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addOrUpdateBrand(@RequestParam String cat,@RequestBody BrandDto brandDto) {
        String escapedCatName= validateHtmlPathVariable.escapeHTMLspecailCharaters(cat);
        return ResponseEntity.ok(brandService.addOrUpdateBrand(brandDto,escapedCatName));
    }
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteBrand(@RequestParam String cat,@RequestBody BrandDto brandDto) {
        String escapedCatName = validateHtmlPathVariable.escapeHTMLspecailCharaters(cat);
        return ResponseEntity.ok(brandService.deleteBrand(brandDto,escapedCatName));
    }

}
