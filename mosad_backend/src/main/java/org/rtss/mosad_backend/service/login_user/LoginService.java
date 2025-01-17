package org.rtss.mosad_backend.service.login_user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.AuthDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserLoginDTO;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthDTO authDTO;
    /*-----------------------------
       * Inject the DtoValidator
       ------------------------------*/
    private final DtoValidator dtoValidator;
    private final UsersRepo usersRepo;

    public LoginService( AuthenticationManager authenticationManager, JwtService jwtService, AuthDTO authDTO, DtoValidator dtoValidator, UsersRepo usersRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authDTO = authDTO;
        this.dtoValidator = dtoValidator;
        this.usersRepo = usersRepo;
    }


    //Generate the refresh token and access token
    public AuthDTO verifyUser(UserLoginDTO userLoginDto) {
        dtoValidator.validate(userLoginDto);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
        if(authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String role=null;
            if(principal instanceof UserDetails userDetails) {
                role=((Users)userDetails).getUserRoles().getRoleName();
             }
            authDTO.setAuthenticated(true);
            authDTO.setAccessToken(jwtService.generateToken(userLoginDto.getUsername(),role));//generate access token
            authDTO.setRefreshToken(jwtService.generateRefreshToken(userLoginDto.getUsername()));//generate refresh token
        }
        else{
            authDTO.setAuthenticated(false);
        }
        return authDTO;
    }

    //responsible for create access token with the refresh token when access token expires
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException,HttpServerErrorException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Request header doesn't contain necessary keyword");
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsernameFromToken(refreshToken);
        if(username != null) {
            //Getting user details from the database according to the token username
            UserDetails userDetails=usersRepo.findByUsername(username).orElseThrow();

            //Validate the given refresh token and generate access token
            if(jwtService.validateToken(refreshToken,userDetails)){
                String role=((Users)userDetails).getUserRoles().getRoleName();
                var accessToken = jwtService.generateToken(username,role);
                //revokeAllUserTokens(user);
                //saveUserToken(user, accessToken);
                authDTO.setAccessToken(accessToken);
                authDTO.setRefreshToken(refreshToken);
                authDTO.setAuthenticated(true);
                new ObjectMapper().writeValue(response.getOutputStream(), authDTO);
            }
        }
    }

}
