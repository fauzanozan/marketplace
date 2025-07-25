package com.marketplace.user_service.controller;

import com.marketplace.user_service.model.User;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.model.response.BaseResponse;
import com.marketplace.user_service.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userSvc;

    public UserController(UserService userSvc) {
        this.userSvc = userSvc;
    }

    @GetMapping("/findAll")
    public ResponseEntity<BaseResponse<List<User>>> getAllUser(){
        var users = userSvc.getAllUser();
        return ResponseEntity.ok(BaseResponse.ok(users));
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse<String>> saveUser(@RequestBody User user) {
        String result = userSvc.saveUser(user);
        if (!"Ok".equals(result)) {
            return ResponseEntity.badRequest().body(BaseResponse.error(result));
        } else {
            return ResponseEntity.ok(BaseResponse.ok("Akun Berhasil Terdaftar"));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        var message = userSvc.login(loginRequest);
        if (!message.equals("Ok")) {
            return ResponseEntity.status(401).body(BaseResponse.error(401, "Unauthorized"));
        }
        return ResponseEntity.ok(BaseResponse.ok(message));

    }
}
