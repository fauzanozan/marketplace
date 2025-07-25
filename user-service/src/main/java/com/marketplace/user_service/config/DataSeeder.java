package com.marketplace.user_service.config;

import com.marketplace.user_service.model.User;
import com.marketplace.user_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUsername("ozan");
            user1.setEmail("ozan@example.com");
            user1.setPassword(passwordEncoder.encode("123456"));

            User user2 = new User();
            user2.setUsername("admin");
            user2.setEmail("admin@example.com");
            user2.setPassword(passwordEncoder.encode("admin"));

            userRepository.saveAll(List.of(user1, user2));
        }
    }
}
