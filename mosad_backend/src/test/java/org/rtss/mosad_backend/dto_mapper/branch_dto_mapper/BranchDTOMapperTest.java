package org.rtss.mosad_backend.dto_mapper.branch_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.branch_dtos.BranchDTO;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.branch_management.BranchContact;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BranchDTOMapperTest {

    //Service
    private BranchDTOMapper branchDTOMapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        branchDTOMapper=new BranchDTOMapper(modelMapper);
    }


    @Test
    void shouldMapBranchToBranchDTO() {
        //Given
        Branch branch = new Branch(
                123123L,
                "Branch1",
                "123",
                "street road",
                "main city",
                new HashSet<>(List.of(new BranchContact())),
                new HashSet<>(List.of(new ItemBranch()))
        );

        //When
        BranchDTO branchDto=branchDTOMapper.branchtoBranchDTO(branch);

        //Then
        assertNotNull(branchDto);
        assertInstanceOf(BranchDTO.class, branchDto);
        assertEquals(branch.getBranchName(),branchDto.getBranchName());
        assertEquals(branch.getAddressNumber(),branchDto.getAddressNumber());
        assertEquals(branch.getStreetName(),branchDto.getStreetName());
        assertEquals(branch.getCity(),branchDto.getCity());

    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenBranchIsNull() {
        //Given -> When & Then
        assertThrows(IllegalArgumentException.class
                , ()->branchDTOMapper.branchtoBranchDTO(null),
                "Branch entity is null");
    }

    @Test
    void shouldMapBranchDTOToBranch() {
        //Given
        BranchDTO branchDTO=new BranchDTO(
                "Branch1",
                "123",
                "my street",
                "MY CITY"
        );

        //When
        Branch branch=branchDTOMapper.branchDTOToBranch(branchDTO);

        //Then
        assertNotNull(branch);
        assertInstanceOf(Branch.class, branch);
        assertEquals(branchDTO.getBranchName(),branch.getBranchName());
        assertEquals(branchDTO.getAddressNumber(),branch.getAddressNumber());
        assertEquals(branchDTO.getStreetName(),branch.getStreetName());
        assertEquals(branchDTO.getCity(),branch.getCity());

    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenBranchDTOIsNull() {
        //Given -> When & Then
        assertThrows(IllegalArgumentException.class,
                ()->branchDTOMapper.branchDTOToBranch(null),
                "BranchDTO is null");

    }
}