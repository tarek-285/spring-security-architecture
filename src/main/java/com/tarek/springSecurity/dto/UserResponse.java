package com.tarek.springSecurity.dto;


import com.tarek.springSecurity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String userName;
    private Set<Role> roles;
}
