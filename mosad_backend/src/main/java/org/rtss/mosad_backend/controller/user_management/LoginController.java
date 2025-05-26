package org.rtss.mosad_backend.controller.user_management;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rtss.mosad_backend.dto.user_dtos.UserLoginDTO;
import org.rtss.mosad_backend.service.login_user.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    // Normal login: Create refresh token and access token
    @PostMapping("/login")
    public void login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody UserLoginDTO userLoginDto) throws IOException {
        loginService.verifyUser(response,userLoginDto);
    }

    //request access token with the valid refresh token
    @PostMapping("/refresh_token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    )throws IOException, HttpServerErrorException {
        loginService.refreshToken(request,response);
    }

}
