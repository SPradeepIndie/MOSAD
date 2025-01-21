package org.rtss.mosad_backend.repository.branch_management;

import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.branch_management.BranchContact;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BranchRepoTest {

    @Autowired
    private BranchRepo branchRepo;

    @Test
    void shouldFindBranchByBranchName() {
        //Give
        Branch branch=new Branch();
        branch.setBranchName("Branch1");
        branch.setAddressNumber("123");
        branch.setStreetName("Street1");
        branch.setCity("City1");

        BranchContact branchContact=new BranchContact();
        branchContact.setContactNumber("1234567890");
        branchContact.setBranch(branch);

        branch.setBranchContacts(new HashSet<>(List.of(branchContact)));
        branch.setItems(new HashSet<>(List.of(new Item())));
        branchRepo.save(branch);

        //When
        Optional<Branch> branches=branchRepo.findBranchByBranchName(branch.getBranchName());

        //Then
        assertTrue(branches.isPresent());
        assertEquals(branch.getBranchName(),branches.get().getBranchName());
    }

    @Test
    void shouldNotFindBranchForNotExistingBranch() {
        //When
        Optional<Branch> branches=branchRepo.findBranchByBranchName("NotExistingBranch");

        //Then
        assertFalse(branches.isPresent());
    }
}