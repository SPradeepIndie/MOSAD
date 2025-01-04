package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.exceptions.ItemDeletionException;
import org.rtss.mosad_backend.service.stock_management_service.ItemDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delete")
public class ItemDeleteController {

    private final ItemDeleteService itemDeleteService;

    @Autowired
    public ItemDeleteController(ItemDeleteService itemDeleteService) {
        this.itemDeleteService = itemDeleteService;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItem(
            @RequestParam String category,
            @RequestParam String brand,
            @RequestParam Integer itemId) {
        try {
            itemDeleteService.deleteItem(category, brand, itemId);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (IllegalArgumentException e) {
            throw new ItemDeletionException("Invalid input provided: " + e.getMessage());
        }
    }
}
