package com.tarek.springSecurity.config;

import com.tarek.springSecurity.dto.LoginRequest;
import com.tarek.springSecurity.dto.UserRequest;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.entity.User;
import com.tarek.springSecurity.repository.Rolerepository;
import com.tarek.springSecurity.repository.UserRepository;
import com.tarek.springSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    public AuthController(UserService userService,
                          AuthenticationManager manager)
    {
        this.userService=userService;
        this.authenticationManager=manager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequest));
    }
    @PostMapping ("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest login)
    {
        Authentication token =new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        );
       Authentication authentication= authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User logged in successfully!");

    }



}
