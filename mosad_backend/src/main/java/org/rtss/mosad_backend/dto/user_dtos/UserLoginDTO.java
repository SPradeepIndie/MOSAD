package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class UserLoginDTO {

    @NotBlank(message = "Username is Mandatory")
    private String username;
    @NotBlank(message = "Password is Mandatory")
    private String password;

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
