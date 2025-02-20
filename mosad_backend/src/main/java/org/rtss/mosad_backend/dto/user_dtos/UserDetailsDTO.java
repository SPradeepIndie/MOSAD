package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UserDetailsDTO {
    @NotNull(message = "User details object can not be null")
    private UserDTO userDto;

    @NotNull(message = "User role object can not be null")
    private UserRoleDTO userRoleDto;

    @NotNull(message = "User contact object can not be null")
    private List<UserContactDTO> userContactDto;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(UserDTO userDto, UserRoleDTO userRoleDto, List<UserContactDTO> userContactDto) {
        this.userDto = userDto;
        this.userRoleDto = userRoleDto;
        this.userContactDto = userContactDto;
    }

    public @NotNull(message = "User details object can not be null") UserDTO getUserDto() {
        return userDto;
    }

    public void setUserDto(@NotNull(message = "User details object can not be null") UserDTO userDto) {
        this.userDto = userDto;
    }

    public @NotNull(message = "User role object can not be null") UserRoleDTO getUserRoleDto() {
        return userRoleDto;
    }

    public void setUserRoleDto(@NotNull(message = "User role object can not be null") UserRoleDTO userRoleDto) {
        this.userRoleDto = userRoleDto;
    }

    public @NotNull(message = "User contact object can not be null") List<UserContactDTO> getUserContactDto() {
        return userContactDto;
    }

    public void setUserContactDto(@NotNull(message = "User contact object can not be null") List<UserContactDTO> userContactDto) {
        this.userContactDto = userContactDto;
    }
}