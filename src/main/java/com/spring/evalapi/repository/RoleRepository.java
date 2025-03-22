package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

   Optional<Role >findByNameAndLevel(String name, String level);
}
