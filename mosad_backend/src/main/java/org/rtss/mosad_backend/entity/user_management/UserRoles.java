package org.rtss.mosad_backend.entity.user_management;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "userRoles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId", unique = true, nullable = false)
    private Integer roleId;

    @Column(name = "roleName", nullable = false, length = 20)
    private String roleName;

    @OneToMany(mappedBy = "userRoles")
    private Set<Users> users;

    public UserRoles(Integer roleId, String roleName, Set<Users> users) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.users = users;
    }

    public UserRoles() {
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                '}';
    }
}
