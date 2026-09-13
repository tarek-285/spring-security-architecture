package com.tarek.springSecurity.controller;

import com.tarek.springSecurity.dto.UserRequest;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService=userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser()
    {
        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }



}
