package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreAtlanderDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreAtlander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemTyreAtlanderDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemTyreAtlanderDTO ItemTyreAtlanderToItemTyreAtlanderDTO(ItemTyreAtlander itemTyreAtlander) {
        return modelMapper.map(itemTyreAtlander, ItemTyreAtlanderDTO.class);
    }

    public ItemTyreAtlander ItemTyreAtlanderDTOToItemTyreAtlander(ItemTyreAtlanderDTO itemTyreAtlanderDTO) {
        return modelMapper.map(itemTyreAtlanderDTO, ItemTyreAtlander.class);
    }
}
