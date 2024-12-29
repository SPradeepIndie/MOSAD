package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemOtherAccessoriesDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemOtherAccessories;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemOtherAccessoriesDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemOtherAccessoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class ItemOtherAccessoriesService {

    @Autowired
    private ItemOtherAccessoriesRepo itemOtherAccessoriesRepo;

    private ItemOtherAccessoriesDTOMapper itemOtherAccessoriesDTOMapper;

    @Autowired
    public void setItemOtherAccessoriesDTOMapper(ItemOtherAccessoriesDTOMapper itemOtherAccessoriesDTOMapper) {
        this.itemOtherAccessoriesDTOMapper = itemOtherAccessoriesDTOMapper;
    }

    public ItemOtherAccessoriesDTO saveItem(ItemOtherAccessoriesDTO itemOtherAccessoriesDTO){
        itemOtherAccessoriesRepo.save(itemOtherAccessoriesDTOMapper.ItemOtherAccessoriesDTOToItemOtherAccessories(itemOtherAccessoriesDTO));
        return itemOtherAccessoriesDTO;
    }

    public List<ItemOtherAccessoriesDTO> getAllItems() {
        List<ItemOtherAccessories> items = itemOtherAccessoriesRepo.findAll();
        List<ItemOtherAccessoriesDTO> itemList = new ArrayList<>();
        for (ItemOtherAccessories itemOtherAccessories : items) {
            ItemOtherAccessoriesDTO dto = itemOtherAccessoriesDTOMapper.ItemOtherAccessoriesToItemOtherAccessoriesDTO(itemOtherAccessories);
            itemList.add(dto);
        }

        return itemList;
    }
    

    public ItemOtherAccessoriesDTO updateItem(ItemOtherAccessoriesDTO itemOtherAccessoriesDTO){
        itemOtherAccessoriesRepo.save(itemOtherAccessoriesDTOMapper.ItemOtherAccessoriesDTOToItemOtherAccessories(itemOtherAccessoriesDTO));
        return itemOtherAccessoriesDTO;
    }

    public boolean deleteUser(ItemOtherAccessoriesDTO itemOtherAccessoriesDTO){
        itemOtherAccessoriesRepo.delete(itemOtherAccessoriesDTOMapper.ItemOtherAccessoriesDTOToItemOtherAccessories(itemOtherAccessoriesDTO));
        return true;
    }
}
