package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserRoleDTO;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.springframework.stereotype.Component;

@Component
public class UserRoleDTOMapper {

    private final ModelMapper modelMapper;

    public UserRoleDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserRoles userRoleDTOToUserRoles(UserRoleDTO userRoleDTO) {
        if(userRoleDTO == null) {
            throw new IllegalArgumentException("userRoleDTO cannot be null");
        }
        return modelMapper.map(userRoleDTO, UserRoles.class);
    }

    public UserRoleDTO userRolesToUserRoleDTO(UserRoles userRoles) {
        if(userRoles == null) {
            throw new IllegalArgumentException("userRoles cannot be null");
        }
        return modelMapper.map(userRoles, UserRoleDTO.class);
    }


}
