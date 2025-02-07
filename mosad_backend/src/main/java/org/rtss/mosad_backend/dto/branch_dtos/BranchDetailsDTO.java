package org.rtss.mosad_backend.dto.branch_dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BranchDetailsDTO {

    @NotNull(message = "BranchDTO can not be bull")
    private BranchDTO branchDto;
    @NotNull(message = "Branch contacts can not be null")
    private List<BranchContactDTO> branchContactDTOList;

    public BranchDetailsDTO() {
    }

    public BranchDetailsDTO(BranchDTO branchDto, List<BranchContactDTO> branchContactDTOList) {
        this.branchDto = branchDto;
        this.branchContactDTOList = branchContactDTOList;
    }

    public BranchDTO getBranchDto() {
        return branchDto;
    }

    public void setBranchDto(BranchDTO branchDto) {
        this.branchDto = branchDto;
    }

    public List<BranchContactDTO> getBranchContactDTOList() {
        return branchContactDTOList;
    }

    public void setBranchContactDTOList(List<BranchContactDTO> branchContactDTOList) {
        this.branchContactDTOList = branchContactDTOList;
    }
}
