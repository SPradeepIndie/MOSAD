package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyre;
import org.springframework.stereotype.Component;

@Component
public class ItemTyreDTOMapper {
    private final ModelMapper modelMapper;

    public ItemTyreDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

   //toEntity
    public ItemTyre toEntity(ItemTyreDTO itemTyreDTO){
        return  modelMapper.map(itemTyreDTO,ItemTyre.class);
    }
}
