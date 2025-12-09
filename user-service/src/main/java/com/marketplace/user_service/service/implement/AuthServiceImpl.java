package com.marketplace.user_service.service.implement;

import com.marketplace.user_service.config.jwt.JwtUtil;
import com.marketplace.user_service.model.User;
import com.marketplace.user_service.model.request.ChangePasswordRequest;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.model.response.LoginResponse;
import com.marketplace.user_service.service.interfaces.AuthService;
import com.marketplace.user_service.service.interfaces.EmailService;
import com.marketplace.user_service.service.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

    @Override
    public String forgotPassword(String email) {
        Optional<User> optUser = userService.findByEmail(email);
        if(optUser.isEmpty()){
            return "Email tidak terdaftar";
        }

        User user = optUser.get();
        String newPassword = newPasswordgenerator(10);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);
        emailService.sendForgotPasswordEmail(user.getEmail(), newPassword);
        return "Password baru berhasil terkirim";
    }

    private String newPasswordgenerator(int len){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        SecureRandom rand = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for(int i = 0; i < len; i++){
            password.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return password.toString();
    }
}
