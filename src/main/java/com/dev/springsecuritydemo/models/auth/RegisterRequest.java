package com.dev.springsecuritydemo.models.auth;

import com.dev.springsecuritydemo.models.myUser.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private Integer age;
    private Role role;
}
