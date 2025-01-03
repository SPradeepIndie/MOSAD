package org.rtss.mosad_backend.dto.user_dtos;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

@Component
public class UserDTO{

    @NotBlank(message = "Username is Mandatory")
    @Size(min = 3,max = 30,message = "Username should between 6 and 50 characters long.")
    private String username;

    @NotBlank(message = "Firstname is Mandatory")
    private String firstName;

    @NotNull(message = "Lastname object can not be null")
    @Size(max = 30,message = "Lastname should maximum 30 characters long")
    private String lastName;

    @NotNull(message = "Email object can not be null")
    @Email(message = "Email should be valid (example@gmail.com)")
    private String email;

    public UserDTO() {
    }
    public UserDTO(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
