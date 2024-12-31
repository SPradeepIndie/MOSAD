package org.rtss.mosad_backend.controller.user_management;

import org.rtss.mosad_backend.service.account_management.AccountManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AccountManagerController {

    private AccountManagementService accountManagementService;

    @PutMapping("/update")
    private ResponseEntity<String> updateInfo(){
        return ResponseEntity.ok().body("Not implemented");
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> deleteAccount(){
        return ResponseEntity.ok().body("Not implemented");
    }

    @PostMapping("/view")
    private ResponseEntity<String> viewUserInfo() {
        return ResponseEntity.ok().body("Not implemented");
    }

    //TODO Forgot password
}
