package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserRoleDTO;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
    public void shouldMapUserRolesDtoToUserRoles() {
        //Given
        UserRoleDTO userRoleDto=new UserRoleDTO(
                "manager"
        );

        //When
        UserRoles userRoles = userRoleDTOMapper.userRoleDTOToUserRoles(userRoleDto);

        //Then
        assertNotNull(userRoles);
        assertEquals(userRoleDto.getRoleName(),userRoles.getRoleName());
    }

    @Test
    void shouldThrowUserRoleDtoNullPointerException(){
     assertThrows(IllegalArgumentException.class, ()->{
         userRoleDTOMapper.userRoleDTOToUserRoles(null);
     },"userRoleDTO cannot be null");
    }

    @Test
    void shouldMapUserRolesToUserRoletDTO() {
        //Given
        UserRoles userRole=new UserRoles(
                1,
                "manager",
                new HashSet<Users>(Arrays.asList(new Users()))
        );

        //When
        UserRoleDTO userRoleDto = userRoleDTOMapper.userRolesToUserRoleDTO(userRole);

        //Then
        assertNotNull(userRoleDto);
        assertEquals(userRole.getRoleName(),userRoleDto.getRoleName());


    }

    @Test
    void shouldThrowRoleNullPointerException(){
        assertThrows(IllegalArgumentException.class, ()->{
            userRoleDTOMapper.userRolesToUserRoleDTO(null);
        },"userRoles cannot be null");
    }


}