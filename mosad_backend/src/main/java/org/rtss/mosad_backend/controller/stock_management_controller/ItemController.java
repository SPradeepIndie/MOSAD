package org.rtss.mosad_backend.controller.stock_management_controller;

import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.service.stock_management_service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Get all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create or update item
    @PostMapping
    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.saveItem(item));
    }

    // Delete item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // Update available quantity
    @PutMapping("/{id}/update-quantity")
    public ResponseEntity<Void> updateQuantity(@PathVariable Long id, @RequestParam int quantityChange) {
        itemService.updateAvailableQuantity(id, quantityChange);
        return ResponseEntity.ok().build();
    }
}
