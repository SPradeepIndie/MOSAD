package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyrePresaDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreLinglong;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyrePresa;
import org.rtss.mosad_backend.model_mapper.stock_management_model_mapper.ItemTyrePresaDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemTyrePresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemTyrePresaService {

    @Autowired
    private ItemTyrePresaRepo itemTyrePresaRepo;

    private ItemTyrePresaDTOMapper itemTyrePresaDTOMapper;

    @Autowired
    public void setItemTyrePresaDTOMapper(ItemTyrePresaDTOMapper itemTyrePresaDTOMapper) {
        this.itemTyrePresaDTOMapper = itemTyrePresaDTOMapper;
    }

    public ItemTyrePresaDTO saveItemTyrePresa(ItemTyrePresaDTO itemTyrePresaDTO){
        itemTyrePresaRepo.save(itemTyrePresaDTOMapper.ItemTyrePresaDtoToItemTyrePresa(itemTyrePresaDTO));
        return itemTyrePresaDTO;
    }


    public List<ItemTyrePresaDTO> getAllItems() {
        List<ItemTyrePresa> items = itemTyrePresaRepo.findAll();
        List<ItemTyrePresaDTO> itemList = new ArrayList<>();
        for (ItemTyrePresa itemTyrePresa : items) {
            ItemTyrePresaDTO dto = itemTyrePresaDTOMapper.ItemTyrePresaToItemTyrePresaDTO(itemTyrePresa);
            itemList.add(dto);
        }

        return itemList;
    }
    
    
    public ItemTyrePresaDTO updateItemTyrePresa(ItemTyrePresaDTO itemTyrePresaDTO){
        itemTyrePresaRepo.save(itemTyrePresaDTOMapper.ItemTyrePresaDtoToItemTyrePresa(itemTyrePresaDTO));
        return itemTyrePresaDTO;
    }

    public boolean deleteItemTyrePresa(ItemTyrePresaDTO itemTyrePresaDTO){
        itemTyrePresaRepo.delete(itemTyrePresaDTOMapper.ItemTyrePresaDtoToItemTyrePresa(itemTyrePresaDTO));
        return true;
    }

    public List<ItemTyrePresa> searchBySize(String tyreSize) {
        return itemTyrePresaRepo.findBytyreSize(tyreSize);
    }
}
