package org.example.goods.repositories;

import org.example.goods.models.entities.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribuhsko Danil
 * @since 19.06.2024
 * <p>
 * Класс для тестирования репозитория для работы с товарами
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest {
    private final ProductRepository productRepository;
    private LocalDateTime now;

    @Autowired
    ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
        now = LocalDateTime.now();
    }

    @BeforeAll
    void addProducts() {
        Product product1 = Product.builder()
                .name("Product 1")
                .description("Product 1's description")
                .updateDate(now)
                .creationDate(now)
                .price(100_000)
                .build();

        Product product2 = Product.builder()
                .name("Product 2")
                .description("Product 2's description")
                .updateDate(now)
                .creationDate(now)
                .price(10_000)
                .build();

        Product product3 = Product.builder()
                .name("Product 3")
                .description("Product 3's description")
                .updateDate(now)
                .creationDate(now)
                .price(1000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @AfterAll
    void deleteProducts() {
        productRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Product product = Product.builder()
                .name("Product")
                .description("Description")
                .updateDate(now)
                .creationDate(now)
                .price(100_000)
                .build();

        long expected = productRepository.count() + 1;
        productRepository.save(product);
        long actual = productRepository.count();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Product expected = productRepository.findAll().get(0);

        Product actual = productRepository.findById(expected.getId()).orElse(null);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void updateTest() {
        Product expected = productRepository.findAll().get(0);
        expected.setName("Updated Name");
        productRepository.save(expected);
        Product actual = productRepository.findById(expected.getId()).get();

        LocalDateTime updatedDate = LocalDateTime.now();
        LocalDateTime expectedUpdateDate = actual.getUpdateDate();

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(updatedDate.getHour(), expectedUpdateDate.getHour());
        Assertions.assertEquals(updatedDate.getMinute(), expectedUpdateDate.getMinute());
    }

    @Test
    void deleteTest() {
        Product product = productRepository.findAll().get(0);
        long expected = productRepository.count() - 1;
        productRepository.delete(product);
        long actual = productRepository.count();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void existByNameAndDescriptionTest() {
        Assertions.assertTrue(productRepository.existsByNameAndDescription("Product 1",
                "Product 1's description"));
        Assertions.assertFalse(productRepository.existsByNameAndDescription("Test",
                "Test description"));
    }
}
