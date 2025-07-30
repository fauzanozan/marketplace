package com.marketplace.user_service.controller;

import com.marketplace.user_service.model.request.ChangePasswordRequest;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.model.response.BaseResponse;
import com.marketplace.user_service.model.response.ChangePasswordResponse;
import com.marketplace.user_service.model.response.LoginResponse;
import com.marketplace.user_service.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(BaseResponse.ok(authService.login(loginRequest)));
    }

    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse<ChangePasswordResponse>> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(BaseResponse.ok(authService.changePassword(request)));
    }
}
