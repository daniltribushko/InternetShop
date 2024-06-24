package org.example.userservice.services.db;

import org.example.userservice.exceptions.authrequest.AuthRequestAlreadyExistException;
import org.example.userservice.exceptions.authrequest.AuthRequestByIdNotFoundException;
import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.entities.AuthRequest;
import org.example.userservice.repositories.AuthRequestRepository;
import org.example.userservice.services.db.imp.AuthRequestDBServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthRequestDBServiceTest {
    @Mock
    private AuthRequestRepository authRequestRepository;
    @InjectMocks
    private AuthRequestDBServiceImp authRequestDBService;
    private List<AuthRequest> requests;
    private LocalDateTime now;

    AuthRequestDBServiceTest(){
        now = LocalDateTime.now();
    }

    @BeforeEach
    void addRequests(){
        AuthRequest authRequest1 = AuthRequest.builder()
                .id(1L)
                .email("Email 1")
                .status(AuthRequestStatus.ACTIVE)
                .code("Code 1")
                .updateDate(now)
                .creationDate(now)
                .build();

        AuthRequest authRequest2 = AuthRequest.builder()
                .id(2L)
                .email("Email 2")
                .status(AuthRequestStatus.ACTIVE)
                .code("Code 2")
                .updateDate(now)
                .creationDate(now)
                .build();

        AuthRequest authRequest3 = AuthRequest.builder()
                .id(3L)
                .email("Email 3")
                .status(AuthRequestStatus.COMPLETED)
                .code("Code 3")
                .updateDate(now)
                .creationDate(now)
                .build();

        AuthRequest authRequest4 = AuthRequest.builder()
                .id(4L)
                .email("Email 4")
                .status(AuthRequestStatus.COMPLETED)
                .code("Code 4")
                .updateDate(now)
                .creationDate(now)
                .build();

        requests = List.of(authRequest1, authRequest2, authRequest3, authRequest4);
    }

    @Test
    void saveTest(){
        Mockito.when(authRequestRepository.existsByEmailAndStatusAndCode("Email 1",
                AuthRequestStatus.ACTIVE,
                "Code 1"))
                .thenReturn(false);

        authRequestDBService.save(requests.get(0));
    }

    @Test
    void saveExceptionTest(){
        Mockito.when(authRequestRepository.existsByEmailAndStatusAndCode("Email 1",
                AuthRequestStatus.ACTIVE,
                "Code 1"))
                .thenReturn(true);

        AuthRequestAlreadyExistException exception = Assertions.assertThrows(AuthRequestAlreadyExistException.class,
                () -> authRequestDBService.save(requests.get(0)));

        Assertions.assertEquals(409, exception.getStatusCode());
        Assertions.assertEquals("AuthRequest with email: Email 1 and status: ACTIVE already exist",
                exception.getMessage());
    }

    @Test
    void findByIdTest(){
        Mockito.when(authRequestRepository.findById(1L))
                .thenReturn(Optional.of(requests.get(0)));

        AuthRequest authRequest = authRequestDBService.findById(1L);

        Assertions.assertNotNull(authRequest);
        Assertions.assertEquals(1L, authRequest.getId());
        Assertions.assertEquals("Email 1", authRequest.getEmail());
        Assertions.assertEquals(AuthRequestStatus.ACTIVE, authRequest.getStatus());
        Assertions.assertEquals("Code 1", authRequest.getCode());
    }

    @Test
    void findByIdExceptionTest(){
        Mockito.when(authRequestRepository.findById(1L))
                .thenReturn(Optional.empty());

        AuthRequestByIdNotFoundException exception = Assertions.assertThrows(
                AuthRequestByIdNotFoundException.class,
                () -> authRequestDBService.findById(1L));

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertEquals("AuthRequest with id 1 not found", exception.getMessage());
    }

    @Test
    void deleteTest(){
        Mockito.when(authRequestRepository.findById(1L))
                .thenReturn(Optional.of(requests.get(0)));

        authRequestDBService.delete(1L);
    }

    @Test
    void findByStatusTest(){
        Mockito.when(authRequestRepository.findAllByStatus(AuthRequestStatus.ACTIVE))
                .thenReturn(List.of(requests.get(0), requests.get(1)));
        Mockito.when(authRequestRepository.findAllByStatus(AuthRequestStatus.COMPLETED))
                .thenReturn(List.of(requests.get(2), requests.get(3)));

        List<AuthRequest> activeRequests = authRequestDBService.findByStatus(AuthRequestStatus.ACTIVE);
        List<AuthRequest> completeRequests = authRequestDBService.findByStatus(AuthRequestStatus.COMPLETED);

        Assertions.assertEquals(2, activeRequests.size());
        Assertions.assertEquals(2, completeRequests.size());
    }

    @Test
    void findByEmailAndStatusAndCodeTest(){
        Mockito.when(authRequestRepository.findByEmailAndStatusAndCode("Email 1",
                AuthRequestStatus.ACTIVE,
                "Code 1"))
                .thenReturn(Optional.of(requests.get(0)));
        Mockito.when(authRequestRepository.findByEmailAndStatusAndCode("Email 3",
                AuthRequestStatus.COMPLETED,
                "Code 3"))
                .thenReturn(Optional.of(requests.get(2)));

        AuthRequest authRequest = authRequestDBService.findByEmailAndStatusAndCode("Email 1",
                AuthRequestStatus.ACTIVE,
                "Code 1");

        AuthRequest authRequest2 = authRequestDBService.findByEmailAndStatusAndCode("Email 3",
                AuthRequestStatus.COMPLETED,
                "Code 3");

        Assertions.assertEquals(1L, authRequest.getId());
        Assertions.assertEquals("Email 1", authRequest.getEmail());
        Assertions.assertEquals(AuthRequestStatus.ACTIVE, authRequest.getStatus());
        Assertions.assertEquals("Code 1", authRequest.getCode());

        Assertions.assertEquals(3L, authRequest2.getId());
        Assertions.assertEquals("Email 3", authRequest2.getEmail());
        Assertions.assertEquals(AuthRequestStatus.COMPLETED, authRequest2.getStatus());
        Assertions.assertEquals("Code 3", authRequest2.getCode());
    }
}
