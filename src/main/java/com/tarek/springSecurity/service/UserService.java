package com.tarek.springSecurity.service;

import com.tarek.springSecurity.dto.UserRequest;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.entity.User;
import com.tarek.springSecurity.repository.Rolerepository;
import com.tarek.springSecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
        UserResponse response= new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getUsername(),savedUser.getRoles());
        return response;
    }

    public List<UserResponse> getAllUsers()
    {
      return  userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }
    public UserResponse getUserById(Long id)
    {
       User savedUser= userRepository.findById(id).orElseThrow(
               ()->new RuntimeException("user not found with this: "+id)

       );
        UserResponse response= new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getUsername(),savedUser.getRoles());
       return response;
    }
    public UserResponse getUserPrifileBYName(String name)
    {
       User user=userRepository.findByUsername(name).orElseThrow(
                ()-> new RuntimeException("No user exist with this name")
        );
       return  mapToResponse(user);

    }

    private UserResponse mapToResponse(User user)
    {
        return new UserResponse(user.getId(), user.getName(), user.getUsername(), user.getRoles());
    }

    @Transactional
    public void deleteUserById(Long id)
    {

       User user= userRepository.findById(id).orElseThrow(()->new RuntimeException("now user found with this id"));
       userRepository.delete(user);

    }
}
