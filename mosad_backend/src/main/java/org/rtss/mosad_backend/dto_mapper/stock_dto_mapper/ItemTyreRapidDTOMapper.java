package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreRapidDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreRapid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemTyreRapidDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemTyreRapidDTO ItemTyreRapidToItemTyreRapidDTO(ItemTyreRapid itemTyreRapid) {
        return modelMapper.map(itemTyreRapid, ItemTyreRapidDTO.class);
    }

    public ItemTyreRapid ItemTyreRapidDtoToItemTyreRapid(ItemTyreRapidDTO itemTyreRapidDTO) {
        return modelMapper.map(itemTyreRapidDTO, ItemTyreRapid.class);
    }
}
