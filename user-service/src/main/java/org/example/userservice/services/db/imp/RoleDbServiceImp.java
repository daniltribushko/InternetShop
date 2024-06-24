package org.example.userservice.services.db.imp;

import org.example.userservice.exceptions.roles.RoleByNameNotFoundException;
import org.example.userservice.models.entities.Role;
import org.example.userservice.repositories.RoleRepository;
import org.example.userservice.services.db.RoleServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleDbServiceImp implements RoleServiceDb {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleDbServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleByNameNotFoundException(name));
    }
}
