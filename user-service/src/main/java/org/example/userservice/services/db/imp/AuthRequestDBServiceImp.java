package org.example.userservice.services.db.imp;

import org.example.userservice.exceptions.authrequest.AuthRequestAlreadyExistException;
import org.example.userservice.exceptions.authrequest.AuthRequestByEmailAndStatusAndCodeNotFoundException;
import org.example.userservice.exceptions.authrequest.AuthRequestByIdNotFoundException;
import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.entities.AuthRequest;
import org.example.userservice.repositories.AuthRequestRepository;
import org.example.userservice.services.db.AuthRequestDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tribusko Danil
 * @since 28.05.2024
 * <p>
 * Реализация сервиса для работы с запросами на авторизацию
 */
@Service
public class AuthRequestDBServiceImp implements AuthRequestDBService {
    private final AuthRequestRepository authRequestRepository;

    @Autowired
    public AuthRequestDBServiceImp(AuthRequestRepository authRequestRepository) {
        this.authRequestRepository = authRequestRepository;
    }

    @Override
    public AuthRequest findByEmailAndStatusAndCode(String email, AuthRequestStatus status, String code) {
        return authRequestRepository.findByEmailAndStatusAndCode(email, status, code)
                .orElseThrow(() -> new AuthRequestByEmailAndStatusAndCodeNotFoundException(email, status));
    }

    @Override
    public void update(AuthRequest authRequest) {
        authRequestRepository.save(authRequest);
    }

    @Override
    public List<AuthRequest> findByStatus(AuthRequestStatus status) {
        return authRequestRepository.findAllByStatus(status);
    }

    @Override
    public void save(AuthRequest entity) {
        String email = entity.getEmail();
        AuthRequestStatus status = entity.getStatus();
        if (authRequestRepository.existsByEmailAndStatusAndCode(email, status, entity.getCode())) {
            throw new AuthRequestAlreadyExistException(email, status);
        }
        authRequestRepository.save(entity);
    }

    @Override
    public AuthRequest findById(Long id) {
        return authRequestRepository.findById(id)
                .orElseThrow(() -> new AuthRequestByIdNotFoundException(id));
    }

    @Override
    public List<AuthRequest> findAll() {
        return authRequestRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        AuthRequest authRequest = findById(id);
        authRequestRepository.delete(authRequest);
    }
}
