package com.personal.demo.repository;


import com.personal.demo.Entity.Role;
import com.personal.demo.Entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
