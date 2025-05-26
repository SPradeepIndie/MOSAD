package org.rtss.mosad_backend.service.register_user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.repository.branch_management.BranchRepo;
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
    private BranchRepo branchRepo;

    //Service to be tested
    private InitialCommandRunnerService service;

    @BeforeEach
    void setUp() {
        passwordEncoder=mock(PasswordEncoder.class);
        when(passwordEncoder.bCryptPasswordEncoder()).thenReturn(mock(BCryptPasswordEncoder.class));
        usersRepo = mock(UsersRepo.class);
        userRolesRepo = mock(UserRolesRepo.class);
        jdbcTemplate = mock(JdbcTemplate.class);
        branchRepo=mock(BranchRepo.class);
        service = new InitialCommandRunnerService(passwordEncoder, usersRepo, userRolesRepo,jdbcTemplate,branchRepo);
    }

    @Test
    void shouldCreateAdminUser() {
        //Given
        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("ADMIN");

        Branch branch=new Branch();
        branch.setBranchName("Mirigama Branch");

        when(usersRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRolesRepo.findUserRolesByRoleName(anyString())).thenReturn(Optional.of(userRoles));
        when(passwordEncoder.bCryptPasswordEncoder().encode(anyString())).thenReturn("encryptedPassword");
        when(branchRepo.findBranchByBranchName("Mirigama Branch")).thenReturn(Optional.of(branch));
        // When
        service.run();

        // Then
        verify(usersRepo).findByUsername("admin");
        verify(userRolesRepo).findUserRolesByRoleName("ADMIN");
        verify(branchRepo,times(2)).findBranchByBranchName("Mirigama Branch");
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