package com.tarek.springSecurity.controller;


import com.tarek.springSecurity.dto.AssignRole;
import com.tarek.springSecurity.dto.CrateRole;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.repository.Rolerepository;
import com.tarek.springSecurity.repository.UserRepository;
import com.tarek.springSecurity.service.RoleService;
import com.tarek.springSecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    public AdminController(RoleService roleService,
                           UserService userService)
    {
        this.roleService=roleService;
        this.userService=userService;

    }


    // -------- Role ManageMent-----

    @PostMapping("/roles")
    public ResponseEntity<Role> addNewRole(@RequestBody CrateRole role)
    {
       return ResponseEntity.status(HttpStatus.CREATED).body( roleService.addRole(role));

    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRole()
    {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRRoles());
    }

    // ------User management -----//


    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUser()

    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }


    @PutMapping("/users/{id}/roles")
    public ResponseEntity<UserResponse> assignRole(@PathVariable Long id,
                                                   @RequestBody AssignRole assignRole)

    {
        UserResponse userResponse= roleService.assignRole(id,assignRole.getRoleIds() );
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
