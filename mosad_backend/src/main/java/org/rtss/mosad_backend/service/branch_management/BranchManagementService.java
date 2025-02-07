package org.rtss.mosad_backend.service.branch_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.branch_dtos.BranchContactDTO;
import org.rtss.mosad_backend.dto.branch_dtos.BranchDTO;
import org.rtss.mosad_backend.dto.branch_dtos.BranchDetailsDTO;
import org.rtss.mosad_backend.dto_mapper.branch_dto_mapper.BranchContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.branch_dto_mapper.BranchDTOMapper;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.branch_management.BranchContact;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.branch_management.BranchRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranchManagementService {
    private final BranchRepo branchRepo;
    private final DtoValidator dtoValidator;
    private final BranchDTOMapper branchDTOMapper;
    private final BranchContactDTOMapper branchContactDTOMapper;
    private final UsersRepo usersRepo;

    public BranchManagementService(BranchRepo branchRepo, DtoValidator dtoValidator, BranchDTOMapper branchDTOMapper, BranchContactDTOMapper branchContactDTOMapper, UsersRepo usersRepo) {
        this.branchRepo = branchRepo;
        this.dtoValidator = dtoValidator;
        this.branchDTOMapper = branchDTOMapper;
        this.branchContactDTOMapper = branchContactDTOMapper;
        this.usersRepo = usersRepo;
    }

    //Delete the branch when branch name gave
    @Transactional
    public ResponseDTO deleteBranch(String branchName) {
        try {
            Branch branch=getBranchByName(branchName);
            branchRepo.delete(branch);
            return new ResponseDTO(true, "Branch deleted successfully");
        }catch (ObjectNotValidException e) {
            return new ResponseDTO(false, "Branch deletion failed: " + e.getMessage());
        }
    }

    //New branch adding
    @Transactional
    public ResponseDTO addBranch(BranchDetailsDTO branchDetailsDto) {
        checkBranchDetailsValidity(branchDetailsDto);
        Branch newBranch=branchDTOMapper.branchDTOToBranch(branchDetailsDto.getBranchDto());
        // Transform and set the branch contacts
        Set<BranchContact> branchContacts =branchDetailsDto.getBranchContactDTOList()
                .stream()
                .map(branchContactDTOMapper::branchContactDTOToBranchContact)
                .collect(Collectors.toSet());

        // Associate each contact with the updated branch
        branchContacts.forEach(branchContact -> branchContact.setBranch(newBranch));
        newBranch.setBranchContacts(branchContacts);
        // Save the new branch details
        branchRepo.saveAndFlush(newBranch);
        return new ResponseDTO(true, "Branch added successfully");

    }

    //Return list of registered branches
    public List<String> getAllBranchesNames() {
        List<Branch> branches=branchRepo.findAll();
        return branches.stream().map(Branch::getBranchName).toList();
    }

    //Return branch details and contact number for a specific branch
    public BranchDetailsDTO getBranchDetailsByName(String branchName) {
        Branch branch=getBranchByName(branchName);
        BranchDTO branchDTO=branchDTOMapper.branchtoBranchDTO(branch);
        List<BranchContactDTO> branchContactDTO=branch.getBranchContacts().stream().map(branchContactDTOMapper::branchContactToBranchContactDTO).toList();
        return new BranchDetailsDTO(branchDTO,branchContactDTO);


    }

    //Update the branch Details by  branch username
    @Transactional
    public ResponseDTO updateBranch(String branchName,BranchDetailsDTO branchDetailsDto) {
        checkBranchDetailsValidity(branchDetailsDto);
        Branch oldBranch=getBranchByName(branchName);
        Branch newBranch=branchDTOMapper.branchDTOToBranch(branchDetailsDto.getBranchDto());
        newBranch.setBranchId(oldBranch.getBranchId());
        // convert branchContactDTO to branchContact set
        Set<BranchContact> branchContacts = branchDetailsDto.getBranchContactDTOList()
                .stream()
                .map(branchContactDTOMapper::branchContactDTOToBranchContact)
                .collect(Collectors.toSet());

        // Associate each contact with the updated branch
        branchContacts.forEach(branchContact -> branchContact.setBranch(newBranch));
        newBranch.setBranchContacts(branchContacts);

        // Save the updated branch details
        branchRepo.saveAndFlush(newBranch);

        return new ResponseDTO(true, "Branch updated successfully");
    }

    //Return the Branch details according to the branch name
    public BranchDetailsDTO getBranchByUsername(String username) {
        if(usersRepo.findByUsername(username).isPresent()){
            return new BranchDetailsDTO();
        }
        return null;
    }

    //Check the branchDetailsDTO is valid
    private void checkBranchDetailsValidity(BranchDetailsDTO branchDetailsDto) {
        dtoValidator.validate(branchDetailsDto);
        dtoValidator.validate(branchDetailsDto.getBranchDto());
        branchDetailsDto.getBranchContactDTOList().forEach(dtoValidator::validate);
    }

    //Return the Branch by the name
    private Branch getBranchByName(String branchName) {
        return branchRepo.findBranchByBranchName(branchName).orElseThrow(
                ()->new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Can not find branch")
        );
    }



}
