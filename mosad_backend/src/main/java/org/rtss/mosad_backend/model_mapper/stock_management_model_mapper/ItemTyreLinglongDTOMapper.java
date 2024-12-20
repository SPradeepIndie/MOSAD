package org.rtss.mosad_backend.model_mapper.stock_management_model_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreLinglongDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreLinglong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemTyreLinglongDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemTyreLinglongDTO ItemTyreLinglongToItemTyreLinglongDTO(ItemTyreLinglong itemTyreLinglong) {
        return modelMapper.map(itemTyreLinglong, ItemTyreLinglongDTO.class);
    }

    public ItemTyreLinglong ItemTyreLinglongDtoTOItemTyreLinglong(ItemTyreLinglongDTO itemTyreLinglongDTO) {
        return modelMapper.map(itemTyreLinglongDTO, ItemTyreLinglong.class);
    }
}
