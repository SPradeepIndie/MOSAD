package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserContactDTO;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.rtss.mosad_backend.entity.user_management.Users;

import static org.junit.jupiter.api.Assertions.*;



class UserContactDTOMapperTest {

    private UserContactDTOMapper userContactDTOMapper;

    @BeforeEach
    void setUp() {
        //Declare model mapper
        ModelMapper modelMapper = new ModelMapper();
        userContactDTOMapper = new UserContactDTOMapper(modelMapper);
    }

    @Test
    void shouldMapuserContactsDTOToUserContacts() {
        //Given
        UserContactDTO userContactDto = new UserContactDTO(
            "0112536722"
        );

        //When
        UserContacts userContacts=userContactDTOMapper.userContactsDTOToUserContacts(userContactDto);

        //Then
        assertNotNull(userContacts);
        assertEquals(userContactDto.getContactNum(),userContacts.getContactNum());
    }

    @Test
    void shouldThrowUserContactDtoNullPointerException(){
        assertThrows(NullPointerException.class, ()->{
            userContactDTOMapper.userContactsDTOToUserContacts(null);
        },"userContactDTO is null");
    }

    @Test
    void shouldMapuserContactsToUserContactDTO() {
        UserContacts userContacts = new UserContacts();
        userContacts.setContactNum("0112536722");
        userContacts.setUser(new Users());

        UserContactDTO userContactDto = userContactDTOMapper.userContactsToUserContactDTO(userContacts);

        assertNotNull(userContactDto);
        assertEquals(userContactDto.getContactNum(),userContacts.getContactNum());
    }

    @Test
    void shouldThrowUserContactNullPointerException(){
        assertThrows(NullPointerException.class, ()->{
            userContactDTOMapper.userContactsToUserContactDTO(null);
        },"userContactDTO is null");
    }
}