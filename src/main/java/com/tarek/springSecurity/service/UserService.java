package com.tarek.springSecurity.service;

import com.tarek.springSecurity.dto.UserRequest;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.entity.User;
import com.tarek.springSecurity.repository.Rolerepository;
import com.tarek.springSecurity.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Rolerepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,
                          Rolerepository rolerepository,
                          PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder= passwordEncoder;
        this.roleRepository=rolerepository;
        this.userRepository=userRepository;
    }
    public UserResponse registerUser(UserRequest userRequest)
    {

        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUserName());
        Role role= roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser=userRepository.save(user);
        UserResponse response= new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getUsername());
        return response;
    }

    public UserResponse getUser(Long id)
    {
       User savedUser= userRepository.findById(id).orElseThrow(
               ()->new RuntimeException("user not found with this: "+id)

       );
        UserResponse response= new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getUsername());
       return response;
    }

}
