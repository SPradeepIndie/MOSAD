package org.rtss.mosad_backend.service.login_user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rtss.mosad_backend.dto.user_dtos.AuthDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserLoginDTO;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthDTO authDTO=new AuthDTO();

    /*-----------------------------
       * Inject the DtoValidator
       ------------------------------*/
    private final DtoValidator dtoValidator;
    private final UsersRepo usersRepo;

    public LoginService( AuthenticationManager authenticationManager, JwtService jwtService,DtoValidator dtoValidator, UsersRepo usersRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.dtoValidator = dtoValidator;
        this.usersRepo = usersRepo;
    }


    //Generate the refresh token and access token
    public void verifyUser(
            HttpServletRequest request,
            HttpServletResponse response,
            UserLoginDTO userLoginDto) throws IOException {
        dtoValidator.validate(userLoginDto);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
        if(authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String role=null;
            Long branchID=null;
            if(principal instanceof UserDetails userDetails) {
                Users user=((Users)userDetails);
                role=user.getUserRoles().getRoleName();
                if(user.getBranch()!=null) {
                    branchID=user.getBranch().getBranchId();
                }
            }
            // Set HTTP-Only Cookie for Refresh Token (Access Token should not be stored in cookies)
            Cookie refreshTokenCookie = new Cookie("refreshToken", jwtService.generateRefreshToken(userLoginDto.getUsername()));
            refreshTokenCookie.setSecure(true); // Only transmit over HTTPS
            refreshTokenCookie.setHttpOnly(true); // Prevent JavaScript access
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(refreshTokenCookie);

            authDTO.setAuthenticated(true);
            authDTO.setAccessToken(jwtService.generateToken(userLoginDto.getUsername(),role));//generate access token
            authDTO.setRole(role);
            authDTO.setBranchId(branchID);
        }
        else{
            authDTO.setAuthenticated(false);
        }
        new ObjectMapper().writeValue(response.getOutputStream(), authDTO);
    }

    //responsible for create access token with the refresh token when access token expires
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException,HttpServerErrorException {
        Cookie[] cookies = request.getCookies();
        String refreshToken ="";
        final String username;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }else{
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED,"No valid refresh token found.");
        }
        if (!refreshToken.isEmpty()) {
            username = jwtService.extractUsernameFromToken(refreshToken);
            if(username != null) {
                //Getting user details from the database according to the token username
                UserDetails userDetails=usersRepo.findByUsername(username).orElseThrow();

                //Validate the given refresh token and generate access token
                if(jwtService.validateToken(refreshToken,userDetails)){
                    String role=((Users)userDetails).getUserRoles().getRoleName();
                    var accessToken = jwtService.generateToken(username,role);
                    authDTO.setAccessToken(accessToken);
                    authDTO.setAuthenticated(true);
                    new ObjectMapper().writeValue(response.getOutputStream(), authDTO);
                } else{
                    throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED,"Invalid refresh token");
                }
            } else{
                throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED,"Invalid refresh token");
            }
        } else {
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED,"Refresh token is empty");
        }

    }


}
