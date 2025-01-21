package org.rtss.mosad_backend.dto_mapper.branch_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.branch_dtos.BranchContactDTO;
import org.rtss.mosad_backend.entity.branch_management.BranchContact;
import org.springframework.stereotype.Component;

@Component
public class BranchContactDTOMapper {
    private final ModelMapper modelMapper;

    public BranchContactDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BranchContact branchContactDTOToBranchContact(BranchContactDTO branchContactDTO) {
        if (branchContactDTO == null) {
            throw new IllegalArgumentException("branchContactDTO cannot be null");
        }
        return modelMapper.map(branchContactDTO, BranchContact.class);
    }

    public BranchContactDTO branchContactToBranchContactDTO(BranchContact branchContact) {
        if(branchContact == null) {
            throw new IllegalArgumentException("branchContact cannot be null");
        }
        return modelMapper.map(branchContact, BranchContactDTO.class);
    }
}
