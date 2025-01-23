package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepo itemRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    // Get all items
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    // Get item by ID
    public Optional<Item> getItemById(Long itemId) {
        return itemRepo.findById(itemId);
    }

    // Save or update item
    public Item saveItem(Item item) {
        return itemRepo.save(item);
    }

    // Delete item by ID
    public void deleteItem(Long itemId) {
        itemRepo.deleteById(itemId);
    }

    // Update available quantity for an item
    public void updateAvailableQuantity(Long itemId, int quantityChange) {
        Optional<Item> itemOptional = itemRepo.findById(itemId);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            int updatedQuantity = item.getAvailableQuantity() + quantityChange;
            if (updatedQuantity < 0) {
                throw new IllegalArgumentException("Insufficient stock for item ID: " + itemId);
            }
            item.setAvailableQuantity(updatedQuantity);
            itemRepo.save(item);
        } else {
            throw new IllegalArgumentException("Item not found for ID: " + itemId);
        }
    }
}
