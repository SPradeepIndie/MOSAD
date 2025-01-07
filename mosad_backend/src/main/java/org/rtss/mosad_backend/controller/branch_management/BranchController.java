package org.rtss.mosad_backend.controller.branch_management;

import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {

    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<String> deleteBranch(@PathVariable("branchId") Long branchId) {
        return ResponseEntity.ok("Deleted Branch: ");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBranch(Branch branch) {
        return ResponseEntity.ok("Created Branch: ");
    }

    @GetMapping("/view/all")
    public ResponseEntity<String> getAllBranchNames() {
        return ResponseEntity.ok("All Branches");
    }

    @GetMapping("/view/name")
    public ResponseEntity<String> getBranchByName(String branchName) {
        String escapedBranchName = HtmlUtils.htmlEscape(branchName);
        return ResponseEntity.ok("Branch: " + escapedBranchName);
    }

    @PutMapping("/update/{branchId}")
    public ResponseEntity<String> updateBranchById(@PathVariable("branchId") Long branchId,
                                                   @RequestBody Branch branch) {
        return ResponseEntity.ok("Updated Branch: ");
    }
}
