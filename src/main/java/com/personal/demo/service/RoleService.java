package com.personal.demo.service;

import com.personal.demo.Entity.Role;
import com.personal.demo.Entity.RoleEnum;
import com.personal.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
