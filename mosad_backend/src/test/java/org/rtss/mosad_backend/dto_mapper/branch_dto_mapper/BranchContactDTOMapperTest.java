package org.rtss.mosad_backend.dto_mapper.branch_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.branch_dtos.BranchContactDTO;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.branch_management.BranchContact;

import static org.junit.jupiter.api.Assertions.*;

class BranchContactDTOMapperTest {

    private BranchContactDTOMapper branchContactDTOMapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        branchContactDTOMapper=new BranchContactDTOMapper(modelMapper);
    }

    @Test
    void shouldMapBranchContactDTOToBranchContact() {
        //Given
        BranchContactDTO branchContactDTO = new BranchContactDTO(
            "01233222222"
        );

        //When
        BranchContact branchContact=branchContactDTOMapper.BranchContactDTOToBranchContact(branchContactDTO);

        //Then
        assertNotNull(branchContact);
        assertInstanceOf(BranchContact.class, branchContact);
        assertEquals(branchContactDTO.getContactNumber(),branchContact.getContactNumber());

    }


    @Test
    void shouldThrowIllegalArgumentExceptionWhenBranchContactDTOIsNull() {
        //Given -> When & Then
        assertThrows(IllegalArgumentException.class
                , ()->branchContactDTOMapper.BranchContactDTOToBranchContact(null),
                "branchContactDTO cannot be null");
    }

    @Test
    void shouldMapBranchContactToBranchContactDTO() {
        //Given
        BranchContact branchContact = new BranchContact(
                123L,
                "1234567890",
                new Branch()

        );

        //When
        BranchContactDTO branchContactDTO=branchContactDTOMapper.BranchContactToBranchContactDTO(branchContact);

        //Then
        assertNotNull(branchContactDTO);
        assertInstanceOf(BranchContactDTO.class, branchContactDTO);
        assertEquals(branchContact.getContactNumber(),branchContactDTO.getContactNumber());

    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenBranchContactIsNull() {
        //Given -> When & Then
        assertThrows(IllegalArgumentException.class
                , ()->branchContactDTOMapper.BranchContactToBranchContactDTO(null),
                "branchContact cannot be null");
    }
}