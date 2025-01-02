package org.rtss.mosad_backend.controller.user_management;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AccountManagerController {

    @PutMapping("/update")
    public ResponseEntity<String> updateInfo(){
        return ResponseEntity.ok().body("Not implemented update method");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(){
        return ResponseEntity.ok().body("Not implemented delete method");
    }

    @PostMapping("/view")
    public ResponseEntity<String> viewUserInfo() {
        return ResponseEntity.ok().body("Not implemented view function");
    }

    //TODO Forgot password
}
