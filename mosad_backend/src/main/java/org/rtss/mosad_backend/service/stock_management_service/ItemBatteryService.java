package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemBatteryDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBattery;
import org.rtss.mosad_backend.model_mapper.stock_management_model_mapper.ItemBatteryDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemBatteryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class ItemBatteryService{

    @Autowired
    private ItemBatteryRepo itemBatteryRepo;

    private ItemBatteryDTOMapper itemBatteryDtoMapper;

    @Autowired
    public void setItemBatteryDtoMapper(ItemBatteryDTOMapper itemBatteryDtoMapper) {
        this.itemBatteryDtoMapper = itemBatteryDtoMapper;
    }


    public ItemBatteryDTO saveItem(ItemBatteryDTO itemBatteryDTO){
        itemBatteryRepo.save(itemBatteryDtoMapper.itemBatteryDtoToItemBattery(itemBatteryDTO));
        return itemBatteryDTO;
    }




    public List<ItemBatteryDTO> getAllItems() {
        List<ItemBattery> items = itemBatteryRepo.findAll();
        List<ItemBatteryDTO> itemList = new ArrayList<>();
        for (ItemBattery itemBattery : items) {
            ItemBatteryDTO dto = itemBatteryDtoMapper.itemBatteryToItemBatteryDto(itemBattery);// Add the mapped DTO to the list
            itemList.add(dto);
        }

        return itemList;
    }


    public ItemBatteryDTO updateItem(ItemBatteryDTO itemBatteryDTO){
        itemBatteryRepo.save(itemBatteryDtoMapper.itemBatteryDtoToItemBattery(itemBatteryDTO));
        return itemBatteryDTO;
    }
    public boolean deleteUser(ItemBatteryDTO itemBatteryDTO){
        itemBatteryRepo.delete(itemBatteryDtoMapper.itemBatteryDtoToItemBattery(itemBatteryDTO));
        return true;
    }


}
