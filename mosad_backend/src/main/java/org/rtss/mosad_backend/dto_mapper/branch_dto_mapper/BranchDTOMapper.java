package org.rtss.mosad_backend.dto_mapper.branch_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.branch_dtos.BranchDTO;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchDTOMapper {
    private final ModelMapper modelMapper;

    public BranchDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BranchDTO branchtoBranchDTO(Branch branch) {
        if(branch == null) {
            throw  new IllegalArgumentException("Branch entity is null");
        }
        return modelMapper.map(branch, BranchDTO.class);
    }

    public Branch branchDTOToBranch(BranchDTO branchDTO) {
        if(branchDTO == null) {
            throw  new IllegalArgumentException("BranchDTO is null");
        }
        return modelMapper.map(branchDTO, Branch.class);
    }
}
