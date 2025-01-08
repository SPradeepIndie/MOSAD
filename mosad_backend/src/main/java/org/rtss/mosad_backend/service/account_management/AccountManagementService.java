package org.rtss.mosad_backend.service.account_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountManagementService {

    public static final String USERNAME_NOT_FOUND_MSG = "Invalid request parameter value with username";
    private final UsersRepo usersRepo;
    private final UserDTOMapper userDTOMapper;
    private final UserContactDTOMapper userContactDTOMapper;
    private final UserRoleDTOMapper userRoleDTOMapper;
    private final DtoValidator dtoValidator;
    private final UserRolesRepo userRolesRepo;

    public AccountManagementService(UsersRepo usersRepo, UserDTOMapper userDTOMapper, UserContactDTOMapper userContactDTOMapper, UserRoleDTOMapper userRoleDTOMapper, DtoValidator dtoValidator, UserRolesRepo userRolesRepo) {
        this.usersRepo = usersRepo;
        this.userDTOMapper = userDTOMapper;
        this.userContactDTOMapper = userContactDTOMapper;
        this.userRoleDTOMapper = userRoleDTOMapper;
        this.dtoValidator = dtoValidator;
        this.userRolesRepo = userRolesRepo;
    }

    //delete a given user
    public ResponseDTO deleteUser(String username) {
        Users user = usersRepo.findByUsername(username)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, USERNAME_NOT_FOUND_MSG));
        usersRepo.delete(user);
        return new ResponseDTO(true,"Successfully deleted "+username);
    }

    //update a given user
    public ResponseDTO updateUser(String username, UserDetailsDTO userUpdateDto){
        Optional<Users> userOptional = usersRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, USERNAME_NOT_FOUND_MSG);
        }
        dtoValidator.validate(userUpdateDto);
        UserDTO userDto=userUpdateDto.getUserDto();
        dtoValidator.validate(userDto);
        Users user=userDTOMapper.userDtoToUsers(userDto);

        //Get the current user details
        Users currentUser=userOptional.get();
        user.setUserId(currentUser.getUserId());
        user.setPassword(currentUser.getPassword());

        UserRoleDTO userRoleDTO=userUpdateDto.getUserRoleDto();
        dtoValidator.validate(userRoleDTO);
        UserRoles userRoles= userRoleDTOMapper.userRoleDTOToUserRoles(userRoleDTO);
        user.setUserRoles(userRolesRepo.findUserRolesByRoleName(userRoles.getRoleName()).orElseThrow(
                () -> new ObjectNotValidException(new HashSet<>(List.of("Unable to find defined user role")))
        ));

        List<UserContactDTO> userContactDtoS=userUpdateDto.getUserContactDto();
        for(UserContactDTO userContactDto:userContactDtoS){
            dtoValidator.validate(userContactDto);
        }
        user.setUserContacts(convertToUserContacts(userContactDtoS,currentUser));
        usersRepo.saveAndFlush(user);

        return new ResponseDTO(true, "Successfully updated " + username);

    }
    //map to the UserContactDto entity.
    private Set<UserContacts> convertToUserContacts(List<UserContactDTO> userContactDtoList,Users user) {
        Set<UserContacts> userContactsSet = new HashSet<>();
        for(UserContactDTO userContactDto:userContactDtoList){
            UserContacts userContact=userContactDTOMapper.userContactsDTOToUserContacts(userContactDto);
            userContact.setUser(user);
            userContactsSet.add(userContact);
        }
        return userContactsSet;
    }

    //Rerun all users usernames
    public List<String> getAllUsers() {
        List<Users> users = usersRepo.findAll();
        List<String> usernames = new ArrayList<>();
        for (Users user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    //Return a specific user details
    public UserDetailsDTO getUser(String username) {
        Optional<Users> userOptional = usersRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, USERNAME_NOT_FOUND_MSG);
        }
        Users user = userOptional.get();
        UserDTO userDto=userDTOMapper.usersToUserDTO(user);
        ArrayList<UserContactDTO> userContactDTOs = user.getUserContacts()
                .stream()
                .map(userContactDTOMapper::userContactsToUserContactDTO)
                .collect(Collectors.toCollection(ArrayList::new));
        UserRoleDTO userRoleDTO=userRoleDTOMapper.userRolesToUserRoleDTO(user.getUserRoles());
        return new UserDetailsDTO(userDto,userRoleDTO,userContactDTOs);

    }
}
