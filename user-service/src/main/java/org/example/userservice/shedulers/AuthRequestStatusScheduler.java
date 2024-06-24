package org.example.userservice.shedulers;

import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.entities.AuthRequest;
import org.example.userservice.services.db.AuthRequestDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class AuthRequestStatusScheduler {
    private final AuthRequestDBService authRequestDBService;

    @Autowired
    public AuthRequestStatusScheduler(AuthRequestDBService authRequestDBService) {
        this.authRequestDBService = authRequestDBService;
    }

    @Scheduled(cron = "0 1 * * * *")
    public void changeStatus(){
        LocalDateTime now = LocalDateTime.now();
        List<AuthRequest> requests = authRequestDBService.findAll();
        requests.stream()
                .filter(r -> Objects.equals(AuthRequestStatus.ACTIVE, r.getStatus()) &&
                            now.isAfter(r.getCreationDate().plusMinutes(1))
                )
                .forEach(r -> {
                    r.setStatus(AuthRequestStatus.COMPLETED);
                    authRequestDBService.update(r);
                });
    }
}
