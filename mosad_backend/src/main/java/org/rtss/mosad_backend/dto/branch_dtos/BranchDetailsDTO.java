package org.rtss.mosad_backend.dto.branch_dtos;

import java.util.List;

public class BranchDetailsDTO {
    private BranchDTO branchDto;
    private List<BranchContactDTO> branchContactDtos;



    public BranchDetailsDTO() {
    }

    public BranchDTO getBranchDto() {
        return branchDto;
    }

    public void setBranchDto(BranchDTO branchDto) {
        this.branchDto = branchDto;
    }



}
