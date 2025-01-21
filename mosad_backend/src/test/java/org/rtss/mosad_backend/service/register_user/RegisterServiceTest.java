package org.rtss.mosad_backend.service.register_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserContactDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserRegistrationDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserRoleDTO;
import org.rtss.mosad_backend.dto_mapper.user_dto_mapper.UserContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.user_dto_mapper.UserDTOMapper;
import org.rtss.mosad_backend.dto_mapper.user_dto_mapper.UserRoleDTOMapper;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    //Service to be tested
    private RegisterService registerService;

    private DtoValidator dtoValidator;
    private UsersRepo usersRepo;
    private UserRolesRepo userRolesRepo;
    private PasswordEncoder passwordEncoder;
    private UserDTOMapper userDTOMapper;
    private UserRoleDTOMapper userRoleDTOMapper ;
    private UserContactDTOMapper userContactDTOMapper;

    private static final String TEST_USERNAME = "sanjaya";
    private static final String TEST_FIRST_NAME = "Sanjaya";
    private static final String TEST_LAST_NAME = "pradeep";
    private static final String TEST_EMAIL = "spkent5@gamil.com";
    private static final String TEST_PASSWORD = "spk@123";
    private static final String TEST_ROLE_NAME = "test1";
    private static final String TEST_CONTACT = "0112536722";
    private UserRoles userRoles;
    private Users user;
    private UserContacts userContacts;
    private UserRegistrationDTO userRegistrationDto;

    @BeforeEach
    void setUp() {
        userDTOMapper = mock(UserDTOMapper.class);
        userRoleDTOMapper = mock(UserRoleDTOMapper.class);
        userContactDTOMapper = mock(UserContactDTOMapper.class);
        passwordEncoder=mock(PasswordEncoder.class);
        when(passwordEncoder.bCryptPasswordEncoder()).thenReturn(mock(BCryptPasswordEncoder.class));
        dtoValidator = mock(DtoValidator.class);
        usersRepo = mock(UsersRepo.class);
        userRolesRepo = mock(UserRolesRepo.class);

        ResponseDTO responseDTO = new ResponseDTO();

        registerService=new RegisterService(userDTOMapper, userRoleDTOMapper, userContactDTOMapper,
                responseDTO,passwordEncoder,usersRepo,userRolesRepo,dtoValidator);

        userContacts=new UserContacts();
        userContacts.setContactNum(TEST_CONTACT);
        Set<UserContacts> userContactsSet=new HashSet<>(List.of(userContacts));

        userRegistrationDto = new UserRegistrationDTO(
                new UserDTO(TEST_USERNAME,TEST_FIRST_NAME,  TEST_LAST_NAME, TEST_EMAIL),
                TEST_PASSWORD,
                new UserRoleDTO(TEST_ROLE_NAME),
                new ArrayList<>(List.of(new UserContactDTO(TEST_CONTACT)))
        );
        userRoles=new UserRoles();
        userRoles.setRoleName(TEST_ROLE_NAME);

        user =new Users();
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_PASSWORD);
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setUserContacts(userContactsSet);
        user.setUserRoles(userRoles);
    }

    //Best Scenario...
    @Test
    void addUser_shouldAddUserAndSaveInDatabase() {
        //Given
        doNothing().when(dtoValidator).validate(any());
        when(usersRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(usersRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRolesRepo.findUserRolesByRoleName(anyString())).thenReturn(Optional.of(userRoles));
        when(passwordEncoder.bCryptPasswordEncoder().encode(anyString())).thenReturn("encodedPassword");
        when(userDTOMapper.userDtoToUsers(any())).thenReturn(user);
        when(userRoleDTOMapper.userRoleDTOToUserRoles(any())).thenReturn(userRoles);
        when(userContactDTOMapper.userContactsDTOToUserContacts(any())).thenReturn(userContacts);
        when(usersRepo.saveAndFlush(any())).thenReturn(null); // Simulate successful save

        ArgumentCaptor<Users> usersCaptor = ArgumentCaptor.forClass(Users.class);
        // When
        ResponseDTO responseDTO = registerService.addUser(userRegistrationDto);


        // Then
        assertTrue(responseDTO.isSuccess());
        assertEquals("User registered successfully", responseDTO.getMessage());
        verify(dtoValidator, times(1)).validate(userRegistrationDto);
        verify(dtoValidator, times(1)).validate(userRegistrationDto.getUserDto());
        verify(dtoValidator, times(1)).validate(userRegistrationDto.getUserRoleDto());
        verify(dtoValidator, times(1)).validate(userRegistrationDto.getUserContactDto().get(0));
        verify(usersRepo, times(1)).findByUsername(TEST_USERNAME);
        verify(usersRepo, times(1)).findByEmail(TEST_EMAIL);
        verify(userRolesRepo, times(2)).findUserRolesByRoleName(TEST_ROLE_NAME);
        verify(usersRepo, times(1)).saveAndFlush(usersCaptor.capture());
        // Assertions based on captured data
        Users savedUser = usersCaptor.getValue();
        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void addUser_ShouldThrowExceptionForDuplicateUsername() {
        // Given
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        UserDTO userDto = new UserDTO();
        userDto.setUsername("duplicateUser");
        userRegistrationDto.setUserDto(userDto);

        when(usersRepo.findByUsername("duplicateUser")).thenReturn(Optional.of(new Users()));

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            registerService.addUser(userRegistrationDto);
        });

        assertEquals("Username already exists", exception.getErrorMessages().iterator().next());
    }

    @Test
    void addUser_ShouldThrowExceptionForDuplicateEmail() {
        // Given
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        UserDTO userDto = new UserDTO();
        userDto.setEmail("duplicate@example.com");
        userRegistrationDto.setUserDto(userDto);

        when(usersRepo.findByEmail("duplicate@example.com")).thenReturn(Optional.of(new Users()));

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            registerService.addUser(userRegistrationDto);
        });

        assertEquals("User email already exists", exception.getErrorMessages().iterator().next());
    }

    @Test
    void addUser_ShouldThrowExceptionForInvalidRole() {
        // Given
        UserRegistrationDTO invalidUserRegistrationDto = new UserRegistrationDTO(
                new UserDTO(TEST_USERNAME,TEST_FIRST_NAME,  TEST_LAST_NAME, TEST_EMAIL),
                TEST_PASSWORD,
                new UserRoleDTO("Invalid Roles"),
                new ArrayList<>(List.of(new UserContactDTO(TEST_CONTACT)))
        );
        UserRoles invaliduserRoles=new UserRoles();
        userRoles.setRoleName("Invalid Roles");

        Users InvalidUser =new Users();
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_PASSWORD);
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setUserRoles(invaliduserRoles);

        doNothing().when(dtoValidator).validate(any());
        when(usersRepo.findByUsername(any())).thenReturn(Optional.empty());
        when(usersRepo.findByEmail(any())).thenReturn(Optional.empty());
        when(userDTOMapper.userDtoToUsers(any())).thenReturn(InvalidUser);
        when(passwordEncoder.bCryptPasswordEncoder().encode(anyString())).thenReturn("encodedPassword");
        when(userRolesRepo.findUserRolesByRoleName("InvalidRole")).thenReturn(Optional.empty());

        // When & Then
        ObjectNotValidException exception = assertThrows(ObjectNotValidException.class, () -> {
            registerService.addUser(invalidUserRegistrationDto);
        });

        assertEquals("User role does not exist", exception.getErrorMessages().iterator().next());
    }
}
