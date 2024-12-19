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
import org.rtss.mosad_backend.entity.UserContacts;
import org.rtss.mosad_backend.entity.UserRoles;
import org.rtss.mosad_backend.entity.Users;
import org.rtss.mosad_backend.repository.UserContactsRepo;
import org.rtss.mosad_backend.repository.UserRolesRepo;
import org.rtss.mosad_backend.repository.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final UserContactsRepo userContactsRepo;

    /*-----------------------------
    * Inject the DtoValidator
    ------------------------------*/
    private final DtoValidator dtoValidator;


    public RegisterService(UserDTOMapper userDTOMapper, UserRoleDTOMapper userRoleDTOMapper, UserContactDTOMapper userContactDTOMapper, ResponseDTO responseDTO, PasswordEncoder passwordEncoder, UsersRepo usersRepo, UserRolesRepo userRolesRepo, UserContactsRepo userContactsRepo, DtoValidator dtoValidator) {
        this.userDTOMapper = userDTOMapper;
        this.userRoleDTOMapper = userRoleDTOMapper;
        this.userContactDTOMapper = userContactDTOMapper;
        this.responseDTO = responseDTO;
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.userRolesRepo = userRolesRepo;
        this.userContactsRepo = userContactsRepo;
        this.dtoValidator = dtoValidator;
    }


    //This method called from the controller class when there is a request for register endpoint
    public ResponseDTO addUser(UserRegistrationDTO userRegistrationDto) {
        var violations = dtoValidator.validate(userRegistrationDto);
        if(!violations.isEmpty()) {
            return generateResponse(true,String.join("|",violations));
        }

        Users users=convertToUsersEntity(validateUserDto(extractUserDTO(userRegistrationDto)));

        users.setPassword(passwordEncode(passwordValidator(extractPassword(userRegistrationDto))));

        UserRoles userRoles= convertToUserRoles(extractUserRoleDTO(userRegistrationDto));
        users.setUserRoles(getUserRoleByname(userRoles.getRoleName()));

        UserContacts userContacts=convertToUserContacts(extractUserContactDTO(userRegistrationDto));

        users.setUserContacts(List.of(userContacts));

        storeData(users,userContacts);

        return generateResponse(true,"User registered successfully");
    }

    //Extract userDTO from UserRegistrationDTO
    private UserDTO extractUserDTO(UserRegistrationDTO userRegistrationDTO) {
        return userRegistrationDTO.getUserDto();
    }

    //react front end basic email validation, basic firstname,lastname,username check is done
    //check the userDto username,email validity(duplicate)
    private UserDTO validateUserDto(UserDTO userDto) {
        if(userDto==null){
            return null;
        }
        if(uniqueUsername(userDto.getUsername())){
            generateResponse(false,"User already exists");
            return null;
        }
        if(validUsername(userDto.getUsername())){
            generateResponse(false,"Username not valid");
            return null;
        }
        if(uniqueEmail(userDto.getEmail())){
            generateResponse(false,"Email already exists");
            return null;
        }
        if(validEmail(userDto.getEmail())){
            generateResponse(false,"Email not valid");
            return null;
        }
        return userDto;
    }
    private boolean uniqueUsername(String username) {
        return usersRepo.findByUsername(username).isPresent();
    }
    private boolean validUsername(String username) {
        return false;
    }
    private boolean uniqueEmail(String email) {
        return usersRepo.findByEmail(email).isPresent();
    }
    private boolean validEmail(String email) {
        return false;
    }
    //map to the Users entity.
    private Users convertToUsersEntity(UserDTO userDto) {
        if(userDto==null){
            return null;
        }
        return userDTOMapper.userDtoToUsers(userDto);
    }

    //Extract password from UserRegistrationDTO
    private String extractPassword(UserRegistrationDTO userRegistrationDTO) {
        return userRegistrationDTO.getPassword();
    }
    //In react frontend check basic password  validation (greater than 6 characters and has alphanumeric characters)
    //password validation
    private String passwordValidator(String pwd){
        return pwd;
    }
    //Encrypt the password
    private String passwordEncode(String pwd) {
        if(pwd==null){
            return null;
        }
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
    private UserRoles getUserRoleByname(String roleName) {
        if( userRolesRepo.findUserRolesByRoleName(roleName).isPresent()){
            return userRolesRepo.findUserRolesByRoleName(roleName).get();
        }
        return null;
    }

    //Extract userContactDTO from UserRegistrationDTO
    private UserContactDTO extractUserContactDTO(UserRegistrationDTO userRegistrationDTO) {
        return userRegistrationDTO.getUserContactDto();
    }
    //map to the UserRoles entity.
    private UserContacts convertToUserContacts(UserContactDTO userContactDto) {
        return userContactDTOMapper.userContactsDTOToUserContacts(userContactDto);
    }

    //store data in the database
    private void storeData(Users users,UserContacts userContacts) {
        usersRepo.saveAndFlush(users);
        //userContactsRepo.saveAndFlush(userContacts);
    }

    //Generate response
    private ResponseDTO generateResponse(boolean success, String responseMsg) {
        responseDTO.setSuccess(success);
        responseDTO.setMessage(responseMsg);
        return responseDTO;
    }

}
