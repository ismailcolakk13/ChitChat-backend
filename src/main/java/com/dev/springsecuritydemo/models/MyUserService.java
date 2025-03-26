package com.dev.springsecuritydemo.models;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    private final MyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    MyUserService(MyUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username +" not found: (Spring)"));

        return new MyUser(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getAge());

    }

    public void createUser(String username, String password, String role, int age) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameNotFoundException(username +" already exists: (Spring)");
        }
        MyUser newUser = MyUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .age(age)
                .build();

        userRepository.save(newUser);
    }


}
