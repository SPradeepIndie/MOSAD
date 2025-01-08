package org.rtss.mosad_backend.controller.user_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserDetailsDTO;
import org.rtss.mosad_backend.service.account_management.AccountManagementService;
import org.rtss.mosad_backend.validator.ValidateHtmlPathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class AccountManagerController {

    private final AccountManagementService accountManagementService;
    private final ValidateHtmlPathVariable validateHtmlPathVariable;


    public AccountManagerController(AccountManagementService accountManagementService, ValidateHtmlPathVariable validateHtmlPathVariable) {
        this.accountManagementService = accountManagementService;
        this.validateHtmlPathVariable = validateHtmlPathVariable;
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<ResponseDTO> updateInfo(
            @PathVariable("username") String username,
            @RequestBody UserDetailsDTO userDetailsDto){
        ResponseDTO responseDTO= accountManagementService.updateUser(validateHtmlPathVariable.escapeHTMLspecailCharaters(username),userDetailsDto);
        return ResponseEntity.accepted().body(responseDTO);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseDTO> deleteAccount(@PathVariable("username") String username){
        ResponseDTO responseDTO= accountManagementService.deleteUser(validateHtmlPathVariable.escapeHTMLspecailCharaters(username));
        return ResponseEntity.accepted().body(responseDTO);
    }

    @GetMapping("/view/{username}")
    public ResponseEntity<UserDetailsDTO> viewUserInfo(@PathVariable("username") String username) {
        UserDetailsDTO userDetailsDto =accountManagementService.getUser(validateHtmlPathVariable.escapeHTMLspecailCharaters(username));
        return ResponseEntity.accepted().body(userDetailsDto);
    }

    @GetMapping("/view/all")
    public ResponseEntity<List<String>> viewAllUsers() {
        List<String> usernames= accountManagementService.getAllUsers();
        return ResponseEntity.ok().body(usernames);
    }


    //TODO Forgot password
}
