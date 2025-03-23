package com.dev.springsecuritydemo;

import com.dev.springsecuritydemo.models.MyUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataInitializer {

    @Bean
    CommandLineRunner initTestUser(MyUserService myUserService) {
        return args -> {
            String username = "ismail";
            String password = "1234";
            String role = "ADMIN";
            int age = 22;

            try {
                myUserService.createUser(username, password, role, age); // şifre hash'lenir
                System.out.println("Test user created: " + username);

            } catch (Exception e) {
                System.out.println("Test user already exists or an error occurred: " + e.getMessage());
            }
        };
    }
}

