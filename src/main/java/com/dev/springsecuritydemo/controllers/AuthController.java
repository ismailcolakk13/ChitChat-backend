package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.models.MyUser;
import com.dev.springsecuritydemo.models.MyUserService;
import com.dev.springsecuritydemo.models.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final MyUserService myUserService;
    private final AuthenticationManager authenticationManager;

    public AuthController(MyUserService myUserService, AuthenticationManager authenticationManager) {
        this.myUserService = myUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody MyUser user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUser userDetails =(MyUser) authentication.getPrincipal();

        UserDTO userDTO = new UserDTO(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getRole(),
                userDetails.getAge()
        );
        return ResponseEntity.ok(userDTO);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MyUser user) {
        myUserService.createUser(user.getUsername(),user.getPassword(),user.getRole(),user.getAge());
        return ResponseEntity.ok("user created(spring)");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
