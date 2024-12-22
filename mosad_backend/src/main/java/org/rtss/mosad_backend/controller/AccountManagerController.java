package org.rtss.mosad_backend.controller;

import org.rtss.mosad_backend.service.account_management.AccountManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
