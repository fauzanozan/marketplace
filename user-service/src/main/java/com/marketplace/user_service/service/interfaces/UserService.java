package com.marketplace.user_service.service.interfaces;

import com.marketplace.user_service.model.User;
import com.marketplace.user_service.model.request.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    String saveUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void updateUser(User user);
}
