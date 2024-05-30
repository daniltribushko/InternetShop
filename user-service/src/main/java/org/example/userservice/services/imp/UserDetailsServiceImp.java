package org.example.userservice.services.imp;

import org.example.userservice.models.entities.User;
import org.example.userservice.services.db.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserDBService userDBService;

    @Autowired
    public UserDetailsServiceImp(UserDBService userDBService) {
        this.userDBService = userDBService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDBService.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "123",
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .toList());
    }
}
