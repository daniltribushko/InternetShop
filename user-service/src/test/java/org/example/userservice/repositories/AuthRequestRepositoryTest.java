package org.example.userservice.repositories;

import org.example.userservice.models.AuthRequestStatus;
import org.example.userservice.models.entities.AuthRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 15.06.2024
 * <p>
 * Класс для тестирования репозитория для запросами на авторизацию
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthRequestRepositoryTest {
    private final AuthRequestRepository authRequestRepository;
    private LocalDateTime now;

    @Autowired
    AuthRequestRepositoryTest(AuthRequestRepository authRequestRepository) {
        this.authRequestRepository = authRequestRepository;
        now = LocalDateTime.now();
    }

    @BeforeAll
    void addRequests() {
        AuthRequest authRequest = AuthRequest
                .builder()
                .code("Code 1")
                .email("email")
                .status(AuthRequestStatus.ACTIVE)
                .creationDate(now)
                .updateDate(now)
                .build();

        AuthRequest authRequest2 = AuthRequest
                .builder()
                .code("Code 2")
                .email("email2")
                .status(AuthRequestStatus.ACTIVE)
                .creationDate(now)
                .updateDate(now)
                .build();

        AuthRequest authRequest3 = AuthRequest
                .builder()
                .code("Code 3")
                .email("email")
                .status(AuthRequestStatus.COMPLETED)
                .creationDate(now)
                .updateDate(now)
                .build();

        AuthRequest authRequest4 = AuthRequest
                .builder()
                .code("Code 4")
                .email("email2")
                .status(AuthRequestStatus.COMPLETED)
                .creationDate(now)
                .updateDate(now)
                .build();

        authRequestRepository.saveAll(List.of(authRequest,
                authRequest2,
                authRequest3,
                authRequest4));
    }

    @AfterAll
    void deleteAllRequests(){
        authRequestRepository.deleteAll();
    }

    @Test
    @Order(1)
    void saveTest(){
        AuthRequest request = AuthRequest
                .builder()
                .code("Code 5")
                .email("email2")
                .status(AuthRequestStatus.ACTIVE)
                .creationDate(now)
                .updateDate(now)
                .build();

        long oldCount = authRequestRepository.count();
        authRequestRepository.save(request);
        long newCount = authRequestRepository.count();

        Assertions.assertEquals(oldCount + 1, newCount);
    }

    @Test
    @Order(2)
    void findByEmailCodeStatusTest(){
        AuthRequest request = authRequestRepository.findByEmailAndStatusAndCode("email2",
                AuthRequestStatus.ACTIVE
                ,"Code 2").orElse(null);

        AuthRequest request2 = authRequestRepository.findByEmailAndStatusAndCode("email2",
                AuthRequestStatus.COMPLETED
                ,"Code 2").orElse(null);

        Assertions.assertNotNull(request);
        Assertions.assertNull(request2);

        Assertions.assertEquals("email2", request.getEmail());
        Assertions.assertEquals("Code 2", request.getCode());
        Assertions.assertEquals(AuthRequestStatus.ACTIVE, request.getStatus());

    }

    @Test
    @Order(3)
    void findByIdTest(){
        AuthRequest request = authRequestRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(request);

        AuthRequest request2 = authRequestRepository.findById(request.getId())
                .orElse(null);

        Assertions.assertNotNull(request2);
        Assertions.assertEquals(request.getEmail(), request2.getEmail());
        Assertions.assertEquals(request.getId(), request2.getId());
        Assertions.assertEquals(request.getStatus(), request2.getStatus());
    }

    @Test
    @Order(4)
    void deleteTest(){
        AuthRequest request = authRequestRepository.findAll()
                .stream()
                .findFirst()
                .get();

        long count = authRequestRepository.count();
        authRequestRepository.delete(request);
        Assertions.assertEquals(count - 1, authRequestRepository.count());
    }

    @Test
    @Order(5)
    void updateTest(){
        AuthRequest request = authRequestRepository
                .findAll()
                .stream()
                .findFirst()
                .get();

        request.setEmail("New email");
        authRequestRepository.save(request);
        request = authRequestRepository.findById(request.getId()).get();
        Assertions.assertEquals("New email", request.getEmail());
    }

    @Test
    @Order(6)
    void existByEmailAndCodeAndStatus(){
        Assertions.assertTrue(authRequestRepository.existsByEmailAndStatusAndCode("email",
                AuthRequestStatus.ACTIVE, "Code 1"));
        Assertions.assertFalse(authRequestRepository.existsByEmailAndStatusAndCode("email",
                AuthRequestStatus.COMPLETED, "Code 1"));
    }

    @Test
    @Order(7)
    void findAllByStatusTest(){
        List<AuthRequest> requests = authRequestRepository.findAllByStatus(AuthRequestStatus.ACTIVE);
        Assertions.assertEquals(2, requests.size());
    }
}
