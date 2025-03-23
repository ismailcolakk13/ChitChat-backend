package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.models.Greeting;
import com.dev.springsecuritydemo.models.MyUser;
import com.dev.springsecuritydemo.models.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final MyUserService myUserService;

    public GreetingController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("===== get greeting =====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


    @PostMapping("/api/register")
    public ResponseEntity<MyUser> login(@RequestBody MyUser user) {
        MyUser newUser = myUserService.createUser(user.getUsername(),user.getPassword(),user.getRole());
        return ResponseEntity.ok(newUser);
    }
}
