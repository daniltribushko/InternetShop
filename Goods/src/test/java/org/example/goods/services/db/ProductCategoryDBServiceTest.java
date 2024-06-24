package org.example.goods.services.db;

import org.example.goods.exceptions.categories.ProductCategoryAlreadyExistException;
import org.example.goods.exceptions.categories.ProductCategoryByIdNotFoundException;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.repositories.ProductCategoryRepository;
import org.example.goods.service.db.imp.ProductCategoryDBServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Tribushko Danil
 * @since 21.06.2024
 * <p>
 * Класс для тестирования сервиса для работы с категориями продуктов в бд
 */
@ExtendWith(MockitoExtension.class)
class ProductCategoryDBServiceTest {
    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @InjectMocks
    private ProductCategoryDBServiceImp productCategoryDBService;

    private List<ProductCategory> categories;

    @BeforeEach
    void addCategories() {
        ProductCategory category1 = ProductCategory.builder()
                .id(1L)
                .title("Category 1")
                .description("Description 1")
                .build();

        ProductCategory category2 = ProductCategory.builder()
                .id(2L)
                .title("Category 2")
                .description("Description 2")
                .build();

        ProductCategory category3 = ProductCategory.builder()
                .id(3L)
                .title("Category 3")
                .description("Description 3")
                .build();

        categories = new ArrayList<>(List.of(category1, category2, category3));
    }

    @Test
    void saveExceptionTest() {
        Mockito.when(productCategoryRepository.existsByTitle("Product 1"))
                .thenReturn(true);

        ProductCategoryAlreadyExistException exception = Assertions.assertThrows(
                ProductCategoryAlreadyExistException.class,
                () -> productCategoryDBService.save(categories.get(0)));
    }

    @Test
    void findByIdTest() {
        ProductCategory expected = categories.get(0);
        Mockito.when(productCategoryRepository.findById(1L))
                .thenReturn(Optional.of(expected));

        ProductCategory actual = productCategoryDBService.findById(1L);

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(productCategoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        ProductCategoryByIdNotFoundException exception = Assertions.assertThrows(
                ProductCategoryByIdNotFoundException.class,
                () -> productCategoryDBService.findById(1L));

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertEquals("Product category with id 1 not found", exception.getMessage());
    }
}
