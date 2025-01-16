package org.rtss.mosad_backend.controller.branch_management;

import org.rtss.mosad_backend.dto.branch_dtos.BranchDTO;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.validator.ValidateHtmlPathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {

    private final ValidateHtmlPathVariable validateHtmlPathVariable;


    public BranchController(ValidateHtmlPathVariable validateHtmlPathVariable) {
        this.validateHtmlPathVariable = validateHtmlPathVariable;
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBranch(@RequestParam Long branchId) {
        return ResponseEntity.ok("Deleted Branch: ");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBranch(@RequestBody BranchDTO branchDto) {
        return ResponseEntity.ok("Created Branch: ");
    }

    @GetMapping("/view/all")
    public ResponseEntity<String> getAllBranchNames() {
        return ResponseEntity.ok("All Branches");
    }

    @GetMapping("/view")
    public ResponseEntity<String> getBranchByName(@RequestParam String branchName) {
        String escapedBranchName = validateHtmlPathVariable.escapeHTMLspecailCharaters(branchName);
        return ResponseEntity.ok("Branch: " + escapedBranchName);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBranchById(@RequestParam Long branchId,
                                                   @RequestBody Branch branch) {
        String escapedBranchId = validateHtmlPathVariable.escapeHTMLspecailCharaters("branchId");
        return ResponseEntity.ok("Updated Branch: ");
    }
}
