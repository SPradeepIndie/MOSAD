package org.rtss.mosad_backend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId",nullable = false, unique = true)
    private Integer userId;
    @Column(name = "username",nullable = false,length = 30)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "firstName",nullable = false,length = 30)
    private String firstName;
    @Column(name = "lastName",length = 30)
    private String lastName;
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "roleId",nullable = false)
    private UserRoles userRoles;

    @OneToMany(mappedBy = "contactNum")
    private List<UserContacts> userContacts;

    public Users() {
    }

    public Users(Integer userId, String username, String password, String firstName, String lastName, String email, UserRoles userRoles, List<UserContacts> userContacts) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoles = userRoles;
        this.userContacts = userContacts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles user_roles) {
        this.userRoles = user_roles;
    }

    public List<UserContacts> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(List<UserContacts> userContacts) {
        this.userContacts = userContacts;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userRoles=" + userRoles +
                ", userContacts=" + userContacts +
                '}';
    }
}
