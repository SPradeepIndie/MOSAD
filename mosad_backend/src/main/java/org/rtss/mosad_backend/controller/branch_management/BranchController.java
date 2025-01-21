package org.rtss.mosad_backend.controller.branch_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.branch_dtos.BranchDetailsDTO;
import org.rtss.mosad_backend.service.branch_management.BranchManagementService;
import org.rtss.mosad_backend.validator.ValidateHtmlPathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {

    private final ValidateHtmlPathVariable validateHtmlPathVariable;
    private final BranchManagementService branchManagementService;


    public BranchController(ValidateHtmlPathVariable validateHtmlPathVariable, BranchManagementService branchManagementService) {
        this.validateHtmlPathVariable = validateHtmlPathVariable;
        this.branchManagementService = branchManagementService;
    }

    //Delete a branch
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteBranch(@RequestParam String branchName) {
        String escapeBranchName= validateHtmlPathVariable.escapeHTMLSpecialCharacters(branchName);
        return ResponseEntity.ok(branchManagementService.deleteBranch(escapeBranchName));
    }

    //Create a new branch
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createBranch(@RequestBody BranchDetailsDTO branchDetailsDto) {
        return ResponseEntity.ok(branchManagementService.addBranch(branchDetailsDto));
    }

    //view all branchNames
    @GetMapping("/view/all")
    public ResponseEntity<List<String>> getAllBranchNames() {
        return ResponseEntity.ok(branchManagementService.getAllBranchesNames());
    }

    //View a specific Branch by it name
    @GetMapping("/view")
    public ResponseEntity<BranchDetailsDTO> getBranchByName(@RequestParam String branchName) {
        String escapedBranchName = validateHtmlPathVariable.escapeHTMLSpecialCharacters(branchName);
        return ResponseEntity.ok(branchManagementService.getBranchDetailsByName(escapedBranchName));
    }

    //Update a branch details
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateBranchById(@RequestParam String branchName,
                                                   @RequestBody BranchDetailsDTO branchDetailsDto) {
        String escapedBranchName = validateHtmlPathVariable.escapeHTMLSpecialCharacters(branchName);
        return ResponseEntity.ok(branchManagementService.updateBranch(escapedBranchName,branchDetailsDto));
    }

    //Get branch Details according to the logged user
    @GetMapping("/view/username")
    public ResponseEntity<BranchDetailsDTO> getBranchByUsername(@RequestParam String username) {
        return ResponseEntity.ok(branchManagementService.getBranchByUsername(username));
    }
}
