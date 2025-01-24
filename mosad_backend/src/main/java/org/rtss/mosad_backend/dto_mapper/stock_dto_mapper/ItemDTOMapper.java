package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDTOMapper {
    private final ModelMapper modelMapper;

    public ItemDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //toDTO

    public ItemDTO toDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    //toEntity

    public Item toEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }
}
