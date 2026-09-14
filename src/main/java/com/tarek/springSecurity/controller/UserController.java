package com.tarek.springSecurity.controller;

import com.tarek.springSecurity.dto.UserRequest;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails
            )
    {
       UserResponse userResponse= userService.getUserPrifileBYName(userDetails.getUsername());
       return ResponseEntity.ok(userResponse);

    }



}
