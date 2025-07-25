package com.marketplace.user_service.service.implement;

import com.marketplace.user_service.model.User;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.repository.UserRepository;
import com.marketplace.user_service.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplement.class);
    private final UserRepository userRepo;

    public UserServiceImplement(UserRepository userRepo) {
        this.userRepo = userRepo;
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
            userRepo.save(user);
            return "Ok";
        } catch (Exception e) {
            logger.error("Error saat menyimpan user", e);
            return "Terjadi kesalahan saat menyimpan user";
        }
    }

    @Override
    public String login(LoginRequest loginRequest) {
        if(existsByUsername(loginRequest.getUsername())) {
            var user = findByUsername(loginRequest.getUsername());
            if (user.isPresent()) {
                if (user.get().getPassword().equals(loginRequest.getPassword())){
                    return "Ok";
                }
            }
        }
        return "Username/Password Salah";
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
}
