package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserRegistrationDTO{

    @NotNull(message = "User details object can not be null")
    private UserDTO userDto;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,}$",
            message = "Password must be at least 6 characters long " +
                    "and contain at least one number " +
                    "and one special character.")
    private String password;

    @NotNull(message = "User role object can not be null")
    private UserRoleDTO userRoleDto;

    @NotNull(message = "User contact object can not be null")
    private ArrayList<UserContactDTO> userContactDto;

    public UserRegistrationDTO(UserDTO userDto, String password, UserRoleDTO userRoleDto, ArrayList<UserContactDTO> userContactDto) {
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

    public @NotNull(message = "User contact object can not be null") ArrayList<UserContactDTO> getUserContactDto() {
        return userContactDto;
    }

    public void setUserContactDto(@NotNull(message = "User contact object can not be null") ArrayList<UserContactDTO> userContactDto) {
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

