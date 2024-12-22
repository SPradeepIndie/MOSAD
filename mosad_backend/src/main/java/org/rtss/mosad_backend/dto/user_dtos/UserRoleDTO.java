package org.rtss.mosad_backend.dto.user_dtos;

import org.springframework.stereotype.Component;

@Component
public class UserRoleDTO{
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
