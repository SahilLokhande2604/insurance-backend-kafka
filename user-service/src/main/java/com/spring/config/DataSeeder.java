package com.spring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.model.User;
import com.spring.repo.UserRepository;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner init(UserRepository repo) {
        return args -> {
            repo.save(new User("admin", "password", "ROLE_ADMIN"));
            repo.save(new User("user", "123456", "ROLE_USER"));
        };
    }
}
