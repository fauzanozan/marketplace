package com.marketplace.user_service.service.implement;

import com.marketplace.user_service.config.jwt.JwtUtil;
import com.marketplace.user_service.model.User;
import com.marketplace.user_service.model.request.ChangePasswordRequest;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.model.response.LoginResponse;
import com.marketplace.user_service.service.interfaces.AuthService;
import com.marketplace.user_service.service.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        return new LoginResponse(jwtUtil.generateToken(loginRequest.getUsername()));
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            if(!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())) {
                return "Password lama tidak sesuai";
            } else {
                user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                userService.updateUser(user.get());
                return "Password berhasil diubah";
            }
        } else {
            return "Username tidak ditemukan";
        }
    }
}
