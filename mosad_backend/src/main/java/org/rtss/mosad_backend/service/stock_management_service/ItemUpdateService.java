package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemUpdateDTO;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemRepo;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ItemUpdateService {

    private final ItemRepo itemRepository;

    @Autowired
    public ItemUpdateService(ItemRepo itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void updateItem(Long itemId, ItemUpdateDTO updateDTO) {
        Optional<Item> existingItem = itemRepository.findById(itemId);

        if (existingItem.isEmpty()) {
            throw new ObjectNotValidException(new HashSet<>(List.of("Item not found")));
        }

        Item item = existingItem.get();
        item.setItemName(updateDTO.getName());
        item.setAvailableQuantity(updateDTO.getQuantity());
        item.setRetailPrice(updateDTO.getPrice());
        item.setBrand(updateDTO.getBrand());
        item.setSize(updateDTO.getSize());

        itemRepository.save(item);
    }
}
