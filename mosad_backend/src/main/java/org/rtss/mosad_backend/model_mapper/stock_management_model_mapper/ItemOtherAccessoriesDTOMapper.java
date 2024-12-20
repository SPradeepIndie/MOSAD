package org.rtss.mosad_backend.model_mapper.stock_management_model_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemOtherAccessoriesDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemOtherAccessories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemOtherAccessoriesDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemOtherAccessoriesDTO ItemOtherAccessoriesToItemOtherAccessoriesDTO(ItemOtherAccessories itemOtherAccessories) {
        return modelMapper.map(itemOtherAccessories, ItemOtherAccessoriesDTO.class);
    }

    public ItemOtherAccessories ItemOtherAccessoriesDTOToItemOtherAccessories(ItemOtherAccessoriesDTO itemOtherAccessoriesDTO) {
        return modelMapper.map(itemOtherAccessoriesDTO, ItemOtherAccessories.class);
    }

}
