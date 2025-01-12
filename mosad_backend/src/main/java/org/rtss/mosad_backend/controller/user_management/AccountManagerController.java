package org.rtss.mosad_backend.controller.user_management;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserDetailsDTO;
import org.rtss.mosad_backend.entity.user_management.Users;
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

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateInfo(
            @RequestParam String username,
            @RequestBody UserDetailsDTO userDetailsDto){
        ResponseDTO responseDTO= accountManagementService.updateUser(validateHtmlPathVariable.escapeHTMLspecailCharaters(username),userDetailsDto);
        return ResponseEntity.accepted().body(responseDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam String username){
        ResponseDTO responseDTO= accountManagementService.deleteUser(validateHtmlPathVariable.escapeHTMLspecailCharaters(username));
        return ResponseEntity.accepted().body(responseDTO);
    }

    @GetMapping("/view")
    public ResponseEntity<UserDetailsDTO> viewUserInfo(@RequestParam String username) {
        UserDetailsDTO userDetailsDto =accountManagementService.getUser(validateHtmlPathVariable.escapeHTMLspecailCharaters(username));
        return ResponseEntity.accepted().body(userDetailsDto);
    }

    @GetMapping("/view/all")
    public ResponseEntity<List<UserDetailsDTO>> viewAllUsers() {
        List<UserDetailsDTO> users= accountManagementService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }


    //TODO Forgot password
}
