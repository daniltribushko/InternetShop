package org.example.userservice.services.db.imp;

import org.example.userservice.exceptions.users.UserAlreadyExistException;
import org.example.userservice.exceptions.users.UserByIdNotFoundException;
import org.example.userservice.models.entities.User;
import org.example.userservice.repositories.UserRepository;
import org.example.userservice.services.db.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Сервис для работы с пользователями
 */
@Service
public class UserDBServiceImp implements UserDBService {
    private final UserRepository userRepository;

    @Autowired
    public UserDBServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)){
            throw new UserAlreadyExistException(email);
        }
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserByIdNotFoundException(id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
