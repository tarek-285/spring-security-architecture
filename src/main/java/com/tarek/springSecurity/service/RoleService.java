package com.tarek.springSecurity.service;

import com.tarek.springSecurity.dto.CrateRole;
import com.tarek.springSecurity.dto.UserResponse;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.entity.User;
import com.tarek.springSecurity.repository.Rolerepository;
import com.tarek.springSecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
   private final Rolerepository roleRepository;
   private final UserRepository userRepository;
   public RoleService(Rolerepository rolerepository,
                      UserRepository userRepository
   )
   {
       this.userRepository=userRepository;
       this.roleRepository =rolerepository;
   }
   public Role addRole(CrateRole crateRole)
   {
       Role role = new Role();
       role.setName(crateRole.getName());
       return roleRepository.save(role);
   }

   public List<Role> getAllRRoles()
   {
      return roleRepository.findAll();
   }

   public UserResponse assignRole(Long id,List<Long> roleIds)
   {

      User user =userRepository.findById(id).orElseThrow(
              ()-> new RuntimeException("no user found ")
      );
      List<Role> roles= roleRepository.findAllById(roleIds);
      if(roles.isEmpty())
      {
          throw new RuntimeException("No valid role foud with given ids");
      }
      user.getRoles().addAll(roles);
      User savedUser=userRepository.save(user);
      return new UserResponse(savedUser.getId(),savedUser.getName(), savedUser.getUsername(), savedUser.getRoles());


   }

}
