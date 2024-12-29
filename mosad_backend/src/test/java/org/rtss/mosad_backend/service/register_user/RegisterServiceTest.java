package org.rtss.mosad_backend.service.register_user;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserContactDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserRegistrationDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserRoleDTO;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private DtoValidator dtoValidator;

    @Mock
    private UsersRepo usersRepo;

    @Mock
    private UserRolesRepo userRolesRepo;


    //Best Scenario...
    @Test
    @Disabled
    void ShouldAddUserAndSaveInDatabase() {
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO(
                new UserDTO("Sanjaya", "sanjaya", "pradeep", "spkent5@gamil.com"),
                "spk@123",
                new UserRoleDTO("test1"),
                new ArrayList<>(List.of(new UserContactDTO("0112536722")))
        );

        // Mock dependencies
        doNothing().when(dtoValidator).validate(any()); // Assume validation passes
        when(usersRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(usersRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRolesRepo.findUserRolesByRoleName(anyString())).thenReturn(Optional.of(new UserRoles()));
        when(usersRepo.saveAndFlush(any())).thenReturn(null); // Simulate successful save

        // Call the method under test
        ResponseDTO responseDTO = registerService.addUser(userRegistrationDto);

        // Verify the expected behavior
        assertTrue(responseDTO.isSuccess());
        assertEquals("User registered successfully", responseDTO.getMessage());

        // Verify interactions with dependencies
        verify(dtoValidator, times(4)).validate(any()); // Validate userRegistrationDto, userDto, userRoleDTO, and each userContactDto
        verify(usersRepo, times(1)).findByUsername(anyString());
        verify(usersRepo, times(1)).findByEmail(anyString());
        verify(userRolesRepo, times(1)).findUserRolesByRoleName(anyString());
        verify(usersRepo, times(1)).saveAndFlush(any());
    }
}
