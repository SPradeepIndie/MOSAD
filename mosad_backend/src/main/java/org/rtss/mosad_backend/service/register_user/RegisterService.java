package org.rtss.mosad_backend.service.register_user;

import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.dto.*;
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
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class RegisterService {

    /*-----------------------------
    * Inject all the mapper classes
    ------------------------------*/
    private final UserDTOMapper userDTOMapper;
    private final UserRoleDTOMapper userRoleDTOMapper;
    private final UserContactDTOMapper userContactDTOMapper;

    /*-----------------------------
    * Inject the custom response class
    ------------------------------*/
    private final ResponseDTO responseDTO;

    /*-----------------------------
    * Inject the password encoder
    ------------------------------*/
    private final PasswordEncoder passwordEncoder;

    /*-----------------------------
    * Inject the repos
    ------------------------------*/
    private final UsersRepo usersRepo;
    private final UserRolesRepo userRolesRepo;

    /*-----------------------------
    * Inject the DtoValidator
    ------------------------------*/
    private final DtoValidator dtoValidator;


    public RegisterService(UserDTOMapper userDTOMapper, UserRoleDTOMapper userRoleDTOMapper, UserContactDTOMapper userContactDTOMapper, ResponseDTO responseDTO, PasswordEncoder passwordEncoder, UsersRepo usersRepo, UserRolesRepo userRolesRepo, DtoValidator dtoValidator) {
        this.userDTOMapper = userDTOMapper;
        this.userRoleDTOMapper = userRoleDTOMapper;
        this.userContactDTOMapper = userContactDTOMapper;
        this.responseDTO = responseDTO;
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.userRolesRepo = userRolesRepo;
        this.dtoValidator = dtoValidator;
    }


    //This method called from the controller class when there is a request for register endpoint
    public ResponseDTO addUser(UserRegistrationDTO userRegistrationDto) {
        dtoValidator.validate(userRegistrationDto);
        UserDTO userDto=extractUserDTO(userRegistrationDto);
        dtoValidator.validate(userDto);

        //check the duplication of username
        uniqueUsername(userDto.getUsername());
        //check the duplication of email
        uniqueEmail(userDto.getEmail());

        Users users=convertToUsersEntity(userDto);

        users.setPassword(passwordEncode(extractPassword(userRegistrationDto)));

        UserRoleDTO userRoleDTO=extractUserRoleDTO(userRegistrationDto);
        dtoValidator.validate(userRoleDTO);
        //check the given user role is in database
        checkUserRoleByName(userRoleDTO.getRoleName());
        UserRoles userRoles= convertToUserRoles(userRoleDTO);

        users.setUserRoles(userRolesRepo.findUserRolesByRoleName(userRoles.getRoleName()).get());

        ArrayList<UserContactDTO> userContactDtoS=extractUserContactDTO(userRegistrationDto);
        for(UserContactDTO userContactDto:userContactDtoS){
            dtoValidator.validate(userContactDto);
        }
        users.setUserContacts(convertToUserContacts(userContactDtoS));

        storeData(users);

        return generateResponse(true,"User registered successfully");
    }

    //Extract userDTO from UserRegistrationDTO
    private UserDTO extractUserDTO(UserRegistrationDTO userRegistrationDTO) {
        return userRegistrationDTO.getUserDto();
    }

    private void uniqueUsername(String username) {
        if(usersRepo.findByUsername(username).isPresent()){
            throw new ObjectNotValidException(new HashSet<>(List.of("User already exists")));
        }
    }

    private void uniqueEmail(String email) {
        if(usersRepo.findByEmail(email).isPresent()){
            throw new ObjectNotValidException(new HashSet<>(List.of("User already exists")));
        }
    }

    //map to the Users entity.
    private Users convertToUsersEntity(UserDTO userDto) {
        return userDTOMapper.userDtoToUsers(userDto);
    }

    //Extract password from UserRegistrationDTO
    private String extractPassword(UserRegistrationDTO userRegistrationDTO) {
        return userRegistrationDTO.getPassword();
    }

    //Encrypt the password
    private String passwordEncode(String pwd) {
        return passwordEncoder.bCryptPasswordEncoder().encode(pwd);
    }

    //Extract userRoleDTO from UserRegistrationDTO
    private UserRoleDTO extractUserRoleDTO(UserRegistrationDTO userRegistrationDto) {
        return userRegistrationDto.getUserRoleDto();
    }

    //map to the UserRoles entity.
    private UserRoles convertToUserRoles(UserRoleDTO UserRoleDto) {
        return userRoleDTOMapper.userRoleDTOToUserRoles(UserRoleDto);
    }

    //check user role in the database
    private void checkUserRoleByName(String roleName) {
        if(userRolesRepo.findUserRolesByRoleName(roleName).isEmpty()){
            throw new ObjectNotValidException(new HashSet<>(List.of("User role does not exist")));
        }
    }

    //Extract userContactDTO from UserRegistrationDTO
    private ArrayList<UserContactDTO> extractUserContactDTO(UserRegistrationDTO userRegistrationDTO) {
        return userRegistrationDTO.getUserContactDto();
    }

    //map to the UserRoles entity.
    private Set<UserContacts> convertToUserContacts(ArrayList<UserContactDTO> userContactDtoList) {
        Set<UserContacts> userContactsSet = new HashSet<>();
        for(UserContactDTO userContactDto:userContactDtoList){
            userContactsSet.add(userContactDTOMapper.userContactsDTOToUserContacts(userContactDto));
        }
        return userContactsSet;
    }

    //store data in the database
    private void storeData(Users user) {
        // Ensure contacts are associated with the user
        user.getUserContacts().forEach(contact-> contact.setUser(user));
        usersRepo.saveAndFlush(user);
    }

    //Generate response
    private ResponseDTO generateResponse(boolean success, String responseMsg) {
        responseDTO.setSuccess(success);
        responseDTO.setMessage(responseMsg);
        return responseDTO;
    }

}
