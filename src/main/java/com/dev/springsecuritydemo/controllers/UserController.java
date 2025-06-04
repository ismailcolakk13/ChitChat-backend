package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.models.myUser.MyUser;
import com.dev.springsecuritydemo.models.myUser.MyUserDTO;
import com.dev.springsecuritydemo.models.myUser.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserDetailsService userDetailsService;

    @GetMapping("/me")
    public ResponseEntity<MyUserDTO> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        MyUser currentUser = (MyUser) userDetailsService.loadUserByUsername(name);
        return ResponseEntity.ok(MyUserMapper.toDTO(currentUser));
    }
}
