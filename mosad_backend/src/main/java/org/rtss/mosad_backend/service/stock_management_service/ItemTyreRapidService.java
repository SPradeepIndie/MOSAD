package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreRapidDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreLinglong;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreRapid;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemTyreRapidDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemTyreRapidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemTyreRapidService {

    @Autowired
    private ItemTyreRapidRepo itemTyreRapidRepo;

    private ItemTyreRapidDTOMapper itemTyreRapidDTOMapper;

    @Autowired
    public void setItemTyreRapidDTOMapper(ItemTyreRapidDTOMapper itemTyreRapidDTOMapper) {
        this.itemTyreRapidDTOMapper = itemTyreRapidDTOMapper;
    }

    public ItemTyreRapidDTO saveItemTyreRapid(ItemTyreRapidDTO itemTyreRapidDTO){
        itemTyreRapidRepo.save(itemTyreRapidDTOMapper.ItemTyreRapidDtoToItemTyreRapid(itemTyreRapidDTO));
        return itemTyreRapidDTO;
    }


    public List<ItemTyreRapidDTO> getAllItems() {
        List<ItemTyreRapid> items = itemTyreRapidRepo.findAll();
        List<ItemTyreRapidDTO> itemList = new ArrayList<>();
        for (ItemTyreRapid itemTyreRapid : items) {
            ItemTyreRapidDTO dto = itemTyreRapidDTOMapper.ItemTyreRapidToItemTyreRapidDTO(itemTyreRapid);
            itemList.add(dto);
        }

        return itemList;
    }
    
    

    public ItemTyreRapidDTO updateItemTyreRapid(ItemTyreRapidDTO itemTyreRapidDTO){
        itemTyreRapidRepo.save(itemTyreRapidDTOMapper.ItemTyreRapidDtoToItemTyreRapid(itemTyreRapidDTO));
        return itemTyreRapidDTO;
    }

    public boolean deleteItemTyreRapid(ItemTyreRapidDTO itemTyreRapidDTO){
        itemTyreRapidRepo.delete(itemTyreRapidDTOMapper.ItemTyreRapidDtoToItemTyreRapid(itemTyreRapidDTO));
        return true;
    }

    public List<ItemTyreRapid> searchBySize(String tyreSize) {
        return itemTyreRapidRepo.findBytyreSize(tyreSize);
    }
}
