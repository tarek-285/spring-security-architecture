package com.tarek.springSecurity.service;

import com.tarek.springSecurity.dto.CrateRole;
import com.tarek.springSecurity.entity.Role;
import com.tarek.springSecurity.repository.Rolerepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
   private final Rolerepository rolerepository;
   public RoleService(Rolerepository rolerepository)
   {
       this.rolerepository =rolerepository;
   }
   public Role addRole(CrateRole crateRole)
   {
       Role role = new Role();
       role.setName(crateRole.getName());
       return rolerepository.save(role);
   }

}
