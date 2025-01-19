package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddTyreItemDTO;
import org.rtss.mosad_backend.service.stock_management_service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add-tyre")
    public ResponseDTO addTyreItem(@RequestBody AddTyreItemDTO addTyreItemDTO) {
        return itemService.addItemTyre(addTyreItemDTO);
    }

    @PutMapping("/update-tyre")
    public ResponseDTO updateTyreItem(@RequestBody AddTyreItemDTO updateTyreItemDTO) {
        return itemService.updateItemTyre(updateTyreItemDTO);
    }

    @DeleteMapping("/delete-tyre/{itemId}")
    public ResponseDTO deleteTyreItem(@PathVariable Long itemId) {
        return itemService.deleteItemTyre(itemId);
    }
}
