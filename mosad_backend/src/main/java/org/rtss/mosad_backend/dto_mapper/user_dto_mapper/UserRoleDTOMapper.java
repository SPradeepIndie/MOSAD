package org.rtss.mosad_backend.dto_mapper.user_dto_mapper;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.user_dtos.UserRoleDTO;
import org.rtss.mosad_backend.entity.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleDTOMapper {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserRoleDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserRoleDTOMapper() {
    }

    public UserRoles userRoleDTOToUserRoles(UserRoleDTO userRoleDTO) {
        return modelMapper.map(userRoleDTO, UserRoles.class);
    }

    public UserRoleDTO userRolesToUserRoleDTO(UserRoles userRoles) {
        return modelMapper.map(userRoles, UserRoleDTO.class);
    }


}
