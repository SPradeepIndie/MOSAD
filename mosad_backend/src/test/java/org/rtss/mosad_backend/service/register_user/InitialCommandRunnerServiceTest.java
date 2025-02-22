package org.rtss.mosad_backend.service.register_user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

class InitialCommandRunnerServiceTest {

    private PasswordEncoder passwordEncoder;
    private UsersRepo usersRepo;
    private UserRolesRepo userRolesRepo;
    private JdbcTemplate jdbcTemplate;

    //Service to be tested
    private InitialCommandRunnerService service;

    @BeforeEach
    void setUp() {
        passwordEncoder=mock(PasswordEncoder.class);
        when(passwordEncoder.bCryptPasswordEncoder()).thenReturn(mock(BCryptPasswordEncoder.class));
        usersRepo = mock(UsersRepo.class);
        userRolesRepo = mock(UserRolesRepo.class);
        jdbcTemplate = mock(JdbcTemplate.class);
        service = new InitialCommandRunnerService(passwordEncoder, usersRepo, userRolesRepo,jdbcTemplate);
    }

    @Test
    void shouldCreateAdminUser() {
        //Given
        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("ADMIN");

        when(usersRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRolesRepo.findUserRolesByRoleName(anyString())).thenReturn(Optional.of(userRoles));
        when(passwordEncoder.bCryptPasswordEncoder().encode(anyString())).thenReturn("encryptedPassword");

        // When
        service.run();

        // Then
        verify(usersRepo).findByUsername("admin");
        verify(userRolesRepo).findUserRolesByRoleName("ADMIN");
        verify(usersRepo).save(any(Users.class));

        verify(usersRepo, times(1)).save(any(Users.class));
    }

    @Test
    void run_shouldNotCreateUserAdminAlreadyRegisteredAdmin() {
        // Mock existing admin
        when(usersRepo.findByUsername("admin")).thenReturn(Optional.of(new Users()));

        // Run the service method
        service.run();

        // Verify no interactions with user creation
        verify(usersRepo, times(0)).save(any(Users.class));
    }

}