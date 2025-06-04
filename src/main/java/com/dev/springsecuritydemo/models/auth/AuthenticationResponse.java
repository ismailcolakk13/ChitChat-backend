package com.dev.springsecuritydemo.models.auth;

import com.dev.springsecuritydemo.models.myUser.MyUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private MyUserDTO user;
    private String token;
}
