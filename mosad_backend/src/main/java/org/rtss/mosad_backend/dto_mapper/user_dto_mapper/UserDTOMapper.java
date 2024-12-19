package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //Constructor
    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //CONVERT UserDTO to Users
    public Users userDtoToUsers(UserDTO userDTO) {
        return modelMapper.map(userDTO, Users.class);
    }

    //CONVERT Users to UserDTO
    public UserDTO usersToUserDTO(Users users) {
        return modelMapper.map(users, UserDTO.class);
    }


}
