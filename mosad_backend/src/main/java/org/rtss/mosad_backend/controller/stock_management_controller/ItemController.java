package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddItemDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public ResponseDTO addTyreItem(@RequestBody AddItemDTO addItemDTO) {
        return itemService.addItemTyre(addItemDTO);
    }

    @PutMapping("/update")
    public ResponseDTO updateTyreItem(@RequestBody AddItemDTO updateItemDTO) {
        return itemService.updateItemTyre(updateItemDTO);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseDTO deleteTyreItem(@PathVariable Long itemId) {
        return itemService.deleteItemTyre(itemId);
    }
}
