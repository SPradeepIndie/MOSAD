package org.rtss.mosad_backend.dto.branch_dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BranchDetailsDTO {

    @NotNull(message = "BranchDTO can not be bull")
    private BranchDTO branchDto;
    @NotNull(message = "Branch contacts can not be null")
    private List<BranchContactDTO> branchContactDtos;

    public BranchDetailsDTO() {
    }

    public BranchDetailsDTO(BranchDTO branchDto, List<BranchContactDTO> branchContactDtos) {
        this.branchDto = branchDto;
        this.branchContactDtos = branchContactDtos;
    }

    public BranchDTO getBranchDto() {
        return branchDto;
    }

    public void setBranchDto(BranchDTO branchDto) {
        this.branchDto = branchDto;
    }

    public List<BranchContactDTO> getBranchContactDtos() {
        return branchContactDtos;
    }

    public void setBranchContactDtos(List<BranchContactDTO> branchContactDtos) {
        this.branchContactDtos = branchContactDtos;
    }
}
