package org.rtss.mosad_backend.dto_mapper.stock_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemBranchDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;
import org.springframework.stereotype.Component;

@Component
public class ItemBranchDTOMapper {
    private final ModelMapper modelMapper;

    public ItemBranchDTOMapper(ModelMapper modelMapper) {
        this.modelMapper=modelMapper;
    }

    //toDTO
    public ItemBranchDTO toDTO(ItemBranch itemBranch){

        return modelMapper.map(itemBranch, ItemBranchDTO.class);
    }

    //toEntity
    public ItemBranch toEntity(ItemBranchDTO itemBranchDTO){
        return modelMapper.map(itemBranchDTO, ItemBranch.class);
    }
}
