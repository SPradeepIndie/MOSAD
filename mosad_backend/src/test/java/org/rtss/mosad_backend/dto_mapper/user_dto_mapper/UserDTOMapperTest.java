package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOMapperTest {

    //Testing Service
    private UserDTOMapper userDTOMapper;

    //Declare model mapper
    private ModelMapper modelMapper;

    //Reference for user Roles and user contacts for Users object
    private UserRoles userRoles;
    private UserContacts userContacts;

    @BeforeEach
    void setUp() {
        modelMapper=new ModelMapper();
        userDTOMapper=new UserDTOMapper(modelMapper);

        //Create UserRoles object for pass to the Users object
        userRoles=new UserRoles();

        //Crete UserContacts object for pass to the Users object
        userContacts=new UserContacts();
    }

    @Test
    public void shouldMapUserDTOToUsers() {
        //Given
        UserDTO dto = new UserDTO(
                "OwnerSp",
                "Sanjaya",
                "Pradeep",
                "mymail@gmail.com"
        );

        //When
        Users users = userDTOMapper.userDtoToUsers(dto);

        //Then
        assertEquals(dto.getUsername(), users.getUsername());
        assertEquals(dto.getFirstName(), users.getFirstName());
        assertEquals(dto.getLastName(), users.getLastName());
        assertEquals(dto.getEmail(), users.getEmail());
    }

    @Test
    void shouldThrowUserDtoIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, ()->{
            userDTOMapper.userDtoToUsers(null);
        },"UserDTO is null");
    }

    @Test
    public void shouldMapUsersToUserDTO() {
        //Given
        Users users = new Users(
                1,
                "OwnerSp",
                "mypasssword",
                "Sanjaya",
                "Pradeep",
                "mymail@gmail.com",
                userRoles,
                new HashSet<UserContacts>(Arrays.asList(userContacts))
        );

        //when
        UserDTO dto = userDTOMapper.usersToUserDTO(users);

        //then
        assertEquals(users.getUsername(), dto.getUsername());
        assertEquals(users.getFirstName(), dto.getFirstName());
        assertEquals(users.getLastName(), dto.getLastName());
        assertEquals(users.getEmail(), dto.getEmail());
    }

    @Test
    void shouldThrowUserIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, ()->{
            userDTOMapper.usersToUserDTO(null);
        },"Users is null");
    }

}