package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddItemDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemService;
import org.springframework.data.repository.query.Param;
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
    public List<AddItemDTO> getAllItems(@Param("category") String category,@Param("brand") String brand,@Param("branchId") Long branchId){

        return itemService.getAllItems(category,brand,branchId);

    }


    @PostMapping("/add")
    public ResponseDTO addTyreItem(@RequestBody AddItemDTO addItemDTO) {
        return itemService.addItem(addItemDTO);
    }

    @PutMapping("/update")
    public ResponseDTO updateTyreItem(@RequestBody AddItemDTO updateItemDTO) {
        return itemService.updateItem(updateItemDTO);
    }

    @DeleteMapping("/delete")
    public ResponseDTO deleteTyreItem(@Param("itemId") Long itemId) {
        return itemService.deleteItem(itemId);
    }

    @GetMapping("/search")
    public List<AddItemDTO> searchItems(@Param("brand") String brand,@Param("size") String size) {
        return itemService.searchItems(brand,size);
    }
}
