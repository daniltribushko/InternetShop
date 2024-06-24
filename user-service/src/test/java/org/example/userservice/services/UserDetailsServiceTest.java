package org.example.userservice.services;

import org.example.userservice.models.entities.Role;
import org.example.userservice.models.entities.User;
import org.example.userservice.services.db.UserDBService;
import org.example.userservice.services.imp.UserDetailsServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
    @Mock
    private UserDBService userDBService;
    @InjectMocks
    private UserDetailsServiceImp userDetailsService;

    @Test
    void loadByUserNameTest(){
        User user = new User("Email", null, null);
        user.setRoles(new HashSet<>(Set.of(new Role("USER"),
                new Role("ADMIN"))));

        org.springframework.security.core.userdetails.User expected =
                new org.springframework.security.core.userdetails.User("Email",
                        "123",
                        List.of(new SimpleGrantedAuthority("USER"),
                                new SimpleGrantedAuthority("ADMIN")));
        Mockito.when(userDBService.findByEmail("Email"))
                .thenReturn(user);
        UserDetails actual = userDetailsService.loadUserByUsername("Email");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getPassword(), actual.getPassword());
        Assertions.assertEquals(expected.getAuthorities().size(), actual.getAuthorities().size());
    }
}
