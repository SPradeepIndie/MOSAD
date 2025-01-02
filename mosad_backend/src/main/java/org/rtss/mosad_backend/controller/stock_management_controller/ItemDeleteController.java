package org.rtss.mosad_backend.controller.stock_management_controller;

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

    private final ItemDeleteService ItemDeleteService;

    @Autowired
    public ItemDeleteController(ItemDeleteService ItemDeleteService) {
        this.ItemDeleteService = ItemDeleteService;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItem(
            @RequestParam String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer itemId) {
        try {
            boolean isDeleted = ItemDeleteService.deleteItem(category, brand, itemId);
            return isDeleted ? ResponseEntity.ok("Item deleted successfully.") :
                    ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
