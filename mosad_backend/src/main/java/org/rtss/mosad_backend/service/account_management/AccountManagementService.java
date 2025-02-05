package org.rtss.mosad_backend.service.account_management;

import org.rtss.mosad_backend.config.security.PasswordEncoder;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.*;
import org.rtss.mosad_backend.dto_mapper.user_dto_mapper.UserContactDTOMapper;
import org.rtss.mosad_backend.dto_mapper.user_dto_mapper.UserDTOMapper;
import org.rtss.mosad_backend.dto_mapper.user_dto_mapper.UserRoleDTOMapper;
import org.rtss.mosad_backend.entity.user_management.UserContacts;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.entity.user_management.UsersOTP;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.repository.user_management.UserRolesRepo;
import org.rtss.mosad_backend.repository.user_management.UsersOTPRepo;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.service.mail_service.EmailService;
import org.rtss.mosad_backend.service.mail_service.MailBody;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.Instant;
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
    private final UsersOTPRepo usersOTPRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom random=new SecureRandom();

    public AccountManagementService(UsersRepo usersRepo, UserDTOMapper userDTOMapper, UserContactDTOMapper userContactDTOMapper, UserRoleDTOMapper userRoleDTOMapper, DtoValidator dtoValidator, UserRolesRepo userRolesRepo, UsersOTPRepo usersOTPRepo, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.userDTOMapper = userDTOMapper;
        this.userContactDTOMapper = userContactDTOMapper;
        this.userRoleDTOMapper = userRoleDTOMapper;
        this.dtoValidator = dtoValidator;
        this.userRolesRepo = userRolesRepo;
        this.usersOTPRepo = usersOTPRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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

    //Return all users usernames
    public List<UserDetailsDTO> getAllUsers() {
        List<Users> users = usersRepo.findAll();
        List<UserDetailsDTO> userDetails = new ArrayList<>();
        if(users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No users found");
        }
        for (Users user : users) {
            userDetails.add(convertToUserDetailsDTO(user));
        }
        return userDetails;
    }

    //send the otp
    public ResponseDTO sendOtp(String email){
        Users user=verifyEmail(email);
        String otp=generateRandomOTPCode();
        sendEmailWithOtp(email,otp);
        saveOTP(otp,user);

        return new ResponseDTO(true,"Successfully sent otp! check you email ");
    }

    //verify otp
    public ResponseDTO verifyOtp(String otp,String email) {
        Users user=verifyEmail(email);
        UsersOTP userOtp=usersOTPRepo.findByOtpTokenAndUser(otp,user).orElseThrow(
                () -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Otp not found for given mail")
        );
        if(userOtp.getOtpExpiryDate().before(Date.from(Instant.now()))){
            usersOTPRepo.deleteById(userOtp.getOtpId());
            throw new HttpServerErrorException(HttpStatus.EXPECTATION_FAILED,"Otp expired");
        }

        return new ResponseDTO(true,"Successfully verified OTP");
    }

    //Change to new password
    public ResponseDTO changeToNewPassword(String newPassword,String email) {
        Users user=verifyEmail(email);
        String encryptedNewPassword=passwordEncoder.bCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedNewPassword);
        usersRepo.saveAndFlush(user);
        return new ResponseDTO(true,"Successfully changed password");
    }

    //Return a specific user details
    public UserDetailsDTO getUser(String username) {
        Optional<Users> userOptional = usersRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, USERNAME_NOT_FOUND_MSG);
        }
        return convertToUserDetailsDTO(userOptional.get());
    }

    //convert given user to a UserDetailsDto object
    private UserDetailsDTO convertToUserDetailsDTO(Users user) {
        UserDTO userDto=userDTOMapper.usersToUserDTO(user);
        ArrayList<UserContactDTO> userContactDTOs = user.getUserContacts()
                .stream()
                .map(userContactDTOMapper::userContactsToUserContactDTO)
                .collect(Collectors.toCollection(ArrayList::new));
        UserRoleDTO userRoleDTO=userRoleDTOMapper.userRolesToUserRoleDTO(user.getUserRoles());
        return new UserDetailsDTO(userDto,userRoleDTO,userContactDTOs);
    }

    //Verify Email
    private Users verifyEmail(String email) {
        return usersRepo.findByEmail(email).orElseThrow(
                () -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Email not found. Please contact admin")
        );
    }

    // Generate a 6-digit OTP
    private String generateRandomOTPCode(){
        int otp=100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    //Save OTP in database
    private void saveOTP(String otpCode,Users user) {
        int otpExpTime = 1000 * 60;
        UsersOTP usersOtp=new UsersOTP();
        usersOtp.setOtpToken(otpCode);
        usersOtp.setOtpExpiryDate(new Date(System.currentTimeMillis()+ otpExpTime));
        user.setUsersOTP(usersOtp);
        usersOtp.setUser(user);
        usersOTPRepo.saveAndFlush(usersOtp);
    }

    //Send the Email with OTP
    private void sendEmailWithOtp(String emailAddress,String otpCode){
        MailBody mailBody=new MailBody(
                emailAddress,
                "",
                "OTP:"+otpCode
        );
        emailService.sendMail(mailBody);
    }
}
