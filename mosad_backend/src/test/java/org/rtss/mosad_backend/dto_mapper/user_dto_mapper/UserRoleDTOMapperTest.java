package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserRoleDTO;
import org.rtss.mosad_backend.entity.UserContacts;
import org.rtss.mosad_backend.entity.UserRoles;
import org.rtss.mosad_backend.entity.Users;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleDTOMapperTest {
    //Testing Service
    private UserRoleDTOMapper userRoleDTOMapper;

    //Declare model mapper
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper=new ModelMapper();
        userRoleDTOMapper=new UserRoleDTOMapper(modelMapper);
    }

    @Test
    public void shouldMapuserRoleDtoToUserRoles() {
        //Given
        UserRoleDTO userRoleDto=new UserRoleDTO(
                "manager"
        );

        //When
        UserRoles userRoles = userRoleDTOMapper.userRoleDTOToUserRoles(userRoleDto);

        //Then
        assertEquals(userRoleDto.getRoleName(),userRoles.getRoleName());
    }


}