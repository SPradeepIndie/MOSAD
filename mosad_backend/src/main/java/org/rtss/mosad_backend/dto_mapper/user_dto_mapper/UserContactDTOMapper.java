package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserContactDTO;
import org.rtss.mosad_backend.entity.UserContacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserContactDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserContactDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserContactDTOMapper() {
    }

    public UserContacts userContactsDTOToUserContacts(UserContactDTO userContactDTO) {
        return modelMapper.map(userContactDTO, UserContacts.class);
    }
    public UserContactDTO userContactsToUserContactDTO(UserContacts userContacts) {
        return modelMapper.map(userContacts, UserContactDTO.class);
    }


}
