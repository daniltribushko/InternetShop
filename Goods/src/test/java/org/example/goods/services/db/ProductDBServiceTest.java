package org.example.goods.services.db;

import org.example.goods.exceptions.products.ProductAlreadyExistException;
import org.example.goods.exceptions.products.ProductByIdNotFoundException;
import org.example.goods.models.entities.Product;
import org.example.goods.repositories.ProductRepository;
import org.example.goods.service.db.imp.ProductDBServiceImp;
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
 * Класс для тестирования сервися для работы с товарами в бд
 */
@ExtendWith(MockitoExtension.class)
class ProductDBServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductDBServiceImp productDBService;

    private List<Product> products;

    @BeforeEach
    void addProducts() {
        Product product1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(1000)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(1000)
                .build();

        Product product3 = Product.builder()
                .id(3L)
                .name("Product 3")
                .description("Description 3")
                .price(1000)
                .build();

        products = new ArrayList<>(List.of(product1, product2, product3));
    }

    @Test
    void saveExceptionTest() {
        Mockito.when(productRepository.existsByNameAndDescription("Product 1", "Description 1"))
                .thenReturn(true);

        ProductAlreadyExistException exception = Assertions.assertThrows(
                ProductAlreadyExistException.class,
                () -> productDBService.save(products.get(0)));

        Assertions.assertEquals(409, exception.getStatusCode());
        Assertions.assertEquals("Product: Product 1 with description: Description 1 already exists",
                exception.getMessage());
    }

    @Test
    void findByIdTest() {
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(products.get(0)));

        Product expected = products.get(0);
        Product actual = productDBService.findById(1L);

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(productRepository.findById(10L))
                .thenReturn(Optional.empty());

        ProductByIdNotFoundException exception = Assertions.assertThrows(
                ProductByIdNotFoundException.class,
                () -> productDBService.findById(10L));

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertEquals("Product with id 10 not found", exception.getMessage());
    }

    @Test
    void existByNameAndDescriptionTest() {
        Mockito.when(productRepository.existsByNameAndDescription("Product 1", "Description 1"))
                .thenReturn(true);
        Mockito.when(productRepository.existsByNameAndDescription("Product 2", "Description 2"))
                .thenReturn(false);
        Assertions.assertTrue(productDBService.existByNameAndDescription("Product 1", "Description 1"));
        Assertions.assertFalse(productDBService.existByNameAndDescription("Product 2", "Description 2"));
    }
}
