package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class UserRoleDTO{

    @NotBlank(message = "User role is Mandatory")
    private String roleName;

    public UserRoleDTO(String roleName) {
        this.roleName = roleName;
    }

    public UserRoleDTO() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserRoleDTO{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
