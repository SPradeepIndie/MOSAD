package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTubeDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemTubeDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemTubeDTO ItemTubeToItemTubeDTO(ItemTube itemTube) {
        return modelMapper.map(itemTube, ItemTubeDTO.class);
    }

    public ItemTube ItemTubeDTOToItemTube(ItemTubeDTO itemTubeDTO) {
        return modelMapper.map(itemTubeDTO, ItemTube.class);
    }
}
