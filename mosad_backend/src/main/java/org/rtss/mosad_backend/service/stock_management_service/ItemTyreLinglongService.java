








package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreLinglongDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreLinglong;
import org.rtss.mosad_backend.model_mapper.stock_management_model_mapper.ItemTyreLinglongDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemTyreLinglongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
@Transactional
public class ItemTyreLinglongService {

    @Autowired
    private ItemTyreLinglongRepo itemTyreLinglongRepo;

    private ItemTyreLinglongDTOMapper itemTyreLinglongDTOMapper;

    @Autowired
    public void setItemTyreLinglongDTOMapper(ItemTyreLinglongDTOMapper itemTyreLinglongDTOMapper) {
        this.itemTyreLinglongDTOMapper = itemTyreLinglongDTOMapper;
    }

    public ItemTyreLinglongDTO saveItemTyreLinglong(ItemTyreLinglongDTO itemTyreLinglongDTO){
        itemTyreLinglongRepo.save(itemTyreLinglongDTOMapper.ItemTyreLinglongDtoTOItemTyreLinglong(itemTyreLinglongDTO));
        return itemTyreLinglongDTO;
    }


    public List<ItemTyreLinglongDTO> getAllItems() {
        List<ItemTyreLinglong> items = itemTyreLinglongRepo.findAll();
        List<ItemTyreLinglongDTO> itemList = new ArrayList<>();
        for (ItemTyreLinglong itemTyreLinglong : items) {
            ItemTyreLinglongDTO dto = itemTyreLinglongDTOMapper.ItemTyreLinglongToItemTyreLinglongDTO(itemTyreLinglong);
            itemList.add(dto);
        }

        return itemList;
    }
    

    public ItemTyreLinglongDTO updateItemTyreLinglong(ItemTyreLinglongDTO itemTyreLinglongDTO){
        itemTyreLinglongRepo.save(itemTyreLinglongDTOMapper.ItemTyreLinglongDtoTOItemTyreLinglong(itemTyreLinglongDTO));
        return itemTyreLinglongDTO;
    }

    public boolean deleteItemTyreLinglong(ItemTyreLinglongDTO itemTyreLinglongDTO){
        itemTyreLinglongRepo.delete(itemTyreLinglongDTOMapper.ItemTyreLinglongDtoTOItemTyreLinglong(itemTyreLinglongDTO));
        return true;
    }
}
