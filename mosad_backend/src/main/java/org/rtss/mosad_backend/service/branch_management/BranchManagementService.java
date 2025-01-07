package org.rtss.mosad_backend.service.branch_management;

import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.repository.branch_management.BranchRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchManagementService {
    private final BranchRepo branchRepo;

    public BranchManagementService(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    public void deleteBranch(Branch branch) {
        branchRepo.delete(branch);
    }

    public void addBranch(Branch branch) {
        branchRepo.save(branch);
    }

    public List<Branch> getAllBranchesNames() {
        return branchRepo.findAll();
    }

    public Branch getBranchDetailsByName(String name) {
        return new Branch();
    }

    public void updateBranch(Branch branch) {
        branchRepo.save(branch);
    }
}
