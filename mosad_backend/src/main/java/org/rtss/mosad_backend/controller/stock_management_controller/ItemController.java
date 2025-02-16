package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddItemDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/view")
    public ResponseEntity<List<AddItemDTO>> getAllItems(@Param("category") String category,@Param("brand") String brand,@Param("branchId") Long branchId){

        return ResponseEntity.ok().body(itemService.getAllItems(category,brand,branchId));

    }


    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addItem(@RequestBody AddItemDTO addItemDTO) {
        return ResponseEntity.ok().body(itemService.addItem(addItemDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateItem(@RequestBody AddItemDTO updateItemDTO) {
        return ResponseEntity.ok().body(itemService.updateItem(updateItemDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteItem(@Param("itemId") Long itemId) {
        return ResponseEntity.ok().body(itemService.deleteItem(itemId));
    }

    @GetMapping("/nn")
    public ResponseEntity<List<AddItemDTO>>searchItems(@Param("brand") String brand,@Param("size") String size,@Param("branchId") Long branchId){
        return ResponseEntity.ok().body(itemService.searchItems(brand,size,branchId));
    }
    @GetMapping("/search")
    public ResponseEntity<List<AddItemDTO>>searchItemsByName(@Param("Category") String Category,@Param("Brand") String Brand,@Param("name") String name,@Param("tyreSize") String tyreSize,@Param("branchId") Long branchId){
        return ResponseEntity.ok().body(itemService.searchItemsByName(Category,Brand,name,tyreSize,branchId));
    }


}
