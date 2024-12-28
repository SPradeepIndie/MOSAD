package org.rtss.mosad_backend.service.stock_management_service;


import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreAtlanderDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreAtlander;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemTyreAtlanderDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemTyreAtlanderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemTyreAtlanderService {

    @Autowired
    private ItemTyreAtlanderRepo itemTyreAtlanderRepo;

    private ItemTyreAtlanderDTOMapper itemTyreAtlanderDTOMapper;

    @Autowired
    public void setItemTyreAtlanderDTOMapper(ItemTyreAtlanderDTOMapper itemTyreAtlanderDTOMapper) {
        this.itemTyreAtlanderDTOMapper = itemTyreAtlanderDTOMapper;
    }

    public ItemTyreAtlanderDTO saveItemTyreAtlander(ItemTyreAtlanderDTO itemTyreAtlanderDTO){
        itemTyreAtlanderRepo.save(itemTyreAtlanderDTOMapper.ItemTyreAtlanderDTOToItemTyreAtlander(itemTyreAtlanderDTO));
        return itemTyreAtlanderDTO;
    }


    public List<ItemTyreAtlanderDTO> getAllItems() {
        List<ItemTyreAtlander> items = itemTyreAtlanderRepo.findAll();
        List<ItemTyreAtlanderDTO> itemList = new ArrayList<>();
        for (ItemTyreAtlander itemTyreAtlander : items) {
            ItemTyreAtlanderDTO dto = itemTyreAtlanderDTOMapper.ItemTyreAtlanderToItemTyreAtlanderDTO(itemTyreAtlander);
            itemList.add(dto);
        }

        return itemList;
    }
    

    public ItemTyreAtlanderDTO updateItemTyreAtlander(ItemTyreAtlanderDTO itemTyreAtlanderDTO){
        itemTyreAtlanderRepo.save(itemTyreAtlanderDTOMapper.ItemTyreAtlanderDTOToItemTyreAtlander(itemTyreAtlanderDTO));
        return itemTyreAtlanderDTO;
    }

    public boolean deleteItemTyreAtlander(ItemTyreAtlanderDTO itemTyreAtlanderDTO){
        itemTyreAtlanderRepo.delete(itemTyreAtlanderDTOMapper.ItemTyreAtlanderDTOToItemTyreAtlander(itemTyreAtlanderDTO));
        return true;
    }




}
