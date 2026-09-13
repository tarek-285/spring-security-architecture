package com.tarek.springSecurity.controller;


import com.tarek.springSecurity.dto.CrateRole;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final RoleService roleService;
    public AdminController(RoleService roleService)
    {
        this.roleService=roleService;

    }

    @PostMapping("/add-role")
    public ResponseEntity<Role> addNewRole(@RequestBody CrateRole role)
    {
       return ResponseEntity.status(HttpStatus.CREATED).body( roleService.addRole(role));

    }


}
