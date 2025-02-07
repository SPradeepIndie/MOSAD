package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    private final ModelMapper modelMapper;

    //Constructor
    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //CONVERT UserDTO to Users
    public Users userDtoToUsers(UserDTO userDTO) {
        if (userDTO == null) {
            throw  new IllegalArgumentException("UserDTO is null");
        }
        return modelMapper.map(userDTO, Users.class);
    }

    //CONVERT Users to UserDTO
    public UserDTO usersToUserDTO(Users users) {
        if(users == null) {
            throw  new IllegalArgumentException("Users is null");
        }
        return modelMapper.map(users, UserDTO.class);
    }


}
