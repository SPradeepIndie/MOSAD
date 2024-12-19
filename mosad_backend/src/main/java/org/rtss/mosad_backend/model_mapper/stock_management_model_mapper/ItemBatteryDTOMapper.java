package org.rtss.mosad_backend.model_mapper.stock_management_model_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemBatteryDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBattery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemBatteryDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemBatteryDTO itemBatteryToItemBatteryDto(ItemBattery itemBattery) {
        return modelMapper.map(itemBattery,ItemBatteryDTO.class);

    }
    public ItemBattery itemBatteryDtoToItemBattery(ItemBatteryDTO itemBatteryDto) {
        return modelMapper.map(itemBatteryDto,ItemBattery.class);
    }




}
