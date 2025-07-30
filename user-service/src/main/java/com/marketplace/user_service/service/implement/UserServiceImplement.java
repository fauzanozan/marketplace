package com.marketplace.user_service.service.implement;

import com.marketplace.user_service.model.User;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.repository.UserRepository;
import com.marketplace.user_service.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplement.class);
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplement(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public String saveUser(User user) {
        try {
            if (userRepo.existsByUsername(user.getUsername()) ||
            userRepo.existsByEmail(user.getEmail())) {
                return "Akun sudah terdaftar";
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return "Ok";
        } catch (Exception e) {
            logger.error("Error saat menyimpan user", e);
            return "Terjadi kesalahan saat menyimpan user";
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }
}
