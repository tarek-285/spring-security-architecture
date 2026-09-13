package com.tarek.springSecurity.repository;

import com.tarek.springSecurity.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Rolerepository extends JpaRepository<Role,Long> {

    @EntityGraph(attributePaths = "name")
    Optional<Role> findByName(String name);


}
