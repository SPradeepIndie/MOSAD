package org.rtss.mosad_backend.service.register_user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.exceptions.DbTableInitException;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminInitialRegisteringServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsersRepo usersRepo;
    @Mock
    private UserRolesRepo userRolesRepo;

    private AdminInitialRegisteringService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AdminInitialRegisteringService(passwordEncoder, usersRepo, userRolesRepo);
    }

    @Test
    @Disabled
    public void shouldCreateAdminUser() {
        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("ADMIN");



        // Mock behavior
        when(usersRepo.findByUsername("admin")).thenReturn(Optional.empty());
        when(userRolesRepo.findUserRolesByRoleName("ADMIN")).thenReturn(Optional.of(userRoles));
        when(passwordEncoder.bCryptPasswordEncoder().encode("admin123")).thenReturn("encryptedPassword");

        // Run the service method
        service.run();

        // Verify interactions
        verify(usersRepo).findByUsername("admin");
        verify(userRolesRepo).findUserRolesByRoleName("ADMIN");
        verify(passwordEncoder).bCryptPasswordEncoder().encode("admin123");
        verify(usersRepo).save(any(Users.class));

        // Assert user creation
        verify(usersRepo, times(1)).save(any(Users.class));
    }

    @Test
    public void shouldNotCreateAdminUser() {
        // Mock existing admin
        when(usersRepo.findByUsername("admin")).thenReturn(Optional.of(new Users()));

        // Run the service method
        service.run();

        // Verify no interactions with user creation
        verify(usersRepo, times(0)).save(any(Users.class));
    }

    @Test
    @Disabled
    public void shouldThrowExceptionWhenNoRole() {
        // Mock DB error
        when(userRolesRepo.findUserRolesByRoleName("notArole")).thenThrow(new DbTableInitException("Error initializing table"));

        // Expect exception
        assertThrows(DbTableInitException.class, () -> service.run());
    }
}