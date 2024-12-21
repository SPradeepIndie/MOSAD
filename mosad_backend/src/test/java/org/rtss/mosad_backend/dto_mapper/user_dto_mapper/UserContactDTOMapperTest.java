package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserContactDTO;
import org.rtss.mosad_backend.entity.UserContacts;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class UserContactDTOMapperTest {

    //Testing Service
    //@InjectMocks
    private UserContactDTOMapper userContactDTOMapper;

    //Declare model mapper
    //@Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
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
    void shouldMapuserContactsToUserContactDTO() {
    }
}