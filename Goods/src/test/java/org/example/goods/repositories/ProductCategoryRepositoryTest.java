package org.example.goods.repositories;

import org.example.goods.models.entities.ProductCategory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 19.06.2024
 * <p>
 * Класс для тестирования репозитоия для работы  категориями товаров
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductCategoryRepositoryTest {
    private ProductCategoryRepository productCategoryRepository;
    private LocalDateTime now;

    @Autowired
    ProductCategoryRepositoryTest(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        now = LocalDateTime.now();
    }

    @BeforeEach
    void addCategories() {
        ProductCategory productCategory = ProductCategory.builder()
                .title("Category 1")
                .description("Category 1's description")
                .updateDate(now)
                .creationDate(now)
                .build();

        ProductCategory productCategory2 = ProductCategory.builder()
                .title("Category 2")
                .description("Category 2's description")
                .updateDate(now)
                .creationDate(now)
                .build();

        ProductCategory productCategory3 = ProductCategory.builder()
                .title("Category 3")
                .description("Category 3's description")
                .updateDate(now)
                .creationDate(now)
                .build();

        productCategoryRepository.saveAll(List.of(productCategory, productCategory2, productCategory3));
    }

    @AfterAll
    void deleteCategories() {
        productCategoryRepository.deleteAll();
    }

    @Test
    void saveTest() {
        ProductCategory productCategory = ProductCategory.builder()
                .title("Category")
                .description("Category description")
                .updateDate(now)
                .creationDate(now)
                .build();
        long expected = productCategoryRepository.count() + 1;
        productCategoryRepository.save(productCategory);
        long actual = productCategoryRepository.count();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        ProductCategory expected = productCategoryRepository.findAll().get(0);
        ProductCategory actual = productCategoryRepository.findById(expected.getId()).orElse(null);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void updateTest() {
        ProductCategory category = productCategoryRepository.findAll().get(0);
        category.setTitle("New title");
        productCategoryRepository.save(category);
        ProductCategory actual = productCategoryRepository.findById(category.getId()).orElse(null);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(category.getId(), actual.getId());
        Assertions.assertEquals("New title", actual.getTitle());
    }

    @Test
    void deleteTest() {
        ProductCategory category = productCategoryRepository.findAll().get(0);
        long expected = productCategoryRepository.count() - 1;
        productCategoryRepository.delete(category);
        long actual = productCategoryRepository.count();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void existByTitleTest() {
        Assertions.assertTrue(productCategoryRepository.existsByTitle("Category 1"));
        Assertions.assertFalse(productCategoryRepository.existsByTitle("Null"));
    }
}
