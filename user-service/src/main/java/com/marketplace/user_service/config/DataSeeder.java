package com.marketplace.user_service.config;

import com.marketplace.user_service.model.User;
import com.marketplace.user_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUsername("ozan");
            user1.setEmail("ozan@example.com");
            user1.setPassword("12345");

            User user2 = new User();
            user2.setUsername("admin");
            user2.setEmail("admin@example.com");
            user2.setPassword("admin123");

            userRepository.saveAll(List.of(user1, user2));
        }
    }
}
