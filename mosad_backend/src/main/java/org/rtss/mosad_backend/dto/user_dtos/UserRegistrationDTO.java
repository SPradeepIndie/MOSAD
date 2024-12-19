package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDTO{

    @NotNull(message = "user details object can not be null")
    private UserDTO userDto;
    @NotNull(message = "user password object can not be null")
    private String password;
    @NotNull(message = "user role object can not be null")
    private UserRoleDTO userRoleDto;
    private UserContactDTO userContactDto;

    public UserRegistrationDTO(UserDTO userDto, String password, UserRoleDTO userRoleDto, UserContactDTO userContactDto) {
        this.userDto = userDto;
        this.password = password;
        this.userRoleDto = userRoleDto;
        this.userContactDto = userContactDto;
    }

    public UserRegistrationDTO() {
    }

    public UserDTO getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDTO userDto) {
        this.userDto = userDto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleDTO getUserRoleDto() {
        return userRoleDto;
    }

    public void setUserRoleDto(UserRoleDTO userRoleDto) {
        this.userRoleDto = userRoleDto;
    }

    public UserContactDTO getUserContactDto() {
        return userContactDto;
    }

    public void setUserContactDto(UserContactDTO userContactDto) {
        this.userContactDto = userContactDto;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "userDto=" + userDto +
                ", password='" + password + '\'' +
                ", userRoleDto=" + userRoleDto +
                ", userContactDto=" + userContactDto +
                '}';
    }
}

