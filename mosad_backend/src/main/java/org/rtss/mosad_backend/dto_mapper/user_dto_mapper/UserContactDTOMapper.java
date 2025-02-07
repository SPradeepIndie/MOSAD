package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserContactDTO;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.springframework.stereotype.Component;

@Component
public class UserContactDTOMapper {

    private final ModelMapper modelMapper;

    public UserContactDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserContacts userContactsDTOToUserContacts(UserContactDTO userContactDTO) {
        if(userContactDTO==null) {
            throw new NullPointerException("userContactDTO is null");
        }
        return modelMapper.map(userContactDTO, UserContacts.class);
    }

    public UserContactDTO userContactsToUserContactDTO(UserContacts userContacts) {
        if(userContacts==null) {
            throw new NullPointerException("userContacts is null");
        }
        return modelMapper.map(userContacts, UserContactDTO.class);
    }


}
