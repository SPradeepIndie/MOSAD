package org.rtss.mosad_backend.controller.user_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserRegistrationDTO;
import org.rtss.mosad_backend.service.register_user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class RegisterController {

    private RegisterService registerService;

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    /*------------------------------------
        This request method is for developing purposes
        for getting csrf token
     ------------------------------------*/
    @GetMapping("/csrf")
    private ResponseEntity<String> getCsrfToken(CsrfToken token) {
        return ResponseEntity.ok(token.getToken());
    }

    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> createAccount(
            @RequestBody UserRegistrationDTO userRegistrationDto)
    {
        ResponseDTO responseDTO=registerService.addUser(userRegistrationDto);
        return ResponseEntity.accepted().body(responseDTO);
    }
}
