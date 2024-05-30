package org.example.userservice.services.db;

import org.example.userservice.models.entities.Role;

public interface RoleServiceDb {
    Role findByName(String name);
}
