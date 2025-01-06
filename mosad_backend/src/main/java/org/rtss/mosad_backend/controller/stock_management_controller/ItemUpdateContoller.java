package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.dto.stock_management_dto.*;
import org.rtss.mosad_backend.service.stock_management_service.ItemUpdateService;
import org.rtss.mosad_backend.exceptions.ItemUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/update")
public class ItemUpdateContoller {
    private ItemUpdateService itemUpdateService;

    @Autowired
    public void ItemUpdateController(ItemUpdateService itemUpdateService) {
        this.itemUpdateService = itemUpdateService;
    }

    public ItemUpdateContoller(ItemUpdateService itemUpdateService) {
        this.itemUpdateService = itemUpdateService;
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemUpdateDTO updateDTO) {
        try {
            itemUpdateService.updateItem(itemId, updateDTO);
            return ResponseEntity.ok("Item updated successfully...");
        } catch (ItemUpdateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
