package org.rtss.mosad_backend.model_mapper.stock_management_model_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyrePresaDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyrePresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemTyrePresaDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemTyrePresaDTO ItemTyrePresaToItemTyrePresaDTO(ItemTyrePresa itemTyrePresa) {
        return modelMapper.map(itemTyrePresa, ItemTyrePresaDTO.class);
    }

    public ItemTyrePresa ItemTyrePresaDtoToItemTyrePresa(ItemTyrePresaDTO itemTyrePresaDTO) {
        return modelMapper.map(itemTyrePresaDTO, ItemTyrePresa.class);
    }
}
