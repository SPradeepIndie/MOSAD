package org.rtss.mosad_backend.service.login_user;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserLoginDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final ResponseDTO responseDTO;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public LoginService(ResponseDTO responseDTO, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.responseDTO = responseDTO;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public ResponseDTO verifyUser(UserLoginDTO userLoginDto) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
        if(authentication.isAuthenticated()) {
            responseDTO.setSuccess(true);
            responseDTO.setMessage(jwtService.generateToken(userLoginDto.getUsername()));
        }
        else{
            responseDTO.setSuccess(false);
            responseDTO.setMessage("fail");
        }

        return responseDTO;
    }
}
