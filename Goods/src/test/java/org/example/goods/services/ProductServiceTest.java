package org.example.goods.services;

import org.example.goods.models.dto.request.CreateProductRequest;
import org.example.goods.models.dto.request.UpdateProductRequest;
import org.example.goods.models.dto.response.ProductResponse;
import org.example.goods.models.entities.Product;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.service.db.ProductCategoryDBService;
import org.example.goods.service.db.ProductDBService;
import org.example.goods.service.imp.ProductServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductDBService productDBService;
    @Mock
    private ProductCategoryDBService productCategoryDBService;
    @InjectMocks
    private ProductServiceImp productService;

    private List<Product> products;
    private List<ProductCategory> categories;
    private List<ProductResponse> responses;
    private LocalDateTime now;

    @BeforeEach
    void addProducts() {
        now = LocalDateTime.now();

        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(1_000)
                .updateDate(now)
                .creationDate(now)
                .category(ProductCategory.builder()
                        .id(2L)
                        .title("Category 2")
                        .description("Description 2")
                        .updateDate(now)
                        .creationDate(now)
                        .build())
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(10_000)
                .updateDate(now)
                .creationDate(now)
                .build();

        Product product3 = Product.builder()
                .id(3L)
                .name("Product 3")
                .description("Description 3")
                .price(1_000_000)
                .updateDate(now)
                .creationDate(now)
                .build();

        products = new ArrayList<>(List.of(product, product2, product3));
    }

    @BeforeEach
    void addCategories() {
        LocalDateTime now = LocalDateTime.now();
        ProductCategory category1 = ProductCategory.builder()
                .id(1L)
                .title("Category 1")
                .description("Description 1")
                .creationDate(now)
                .updateDate(now)
                .products(new HashSet<>())
                .build();

        ProductCategory category2 = ProductCategory.builder()
                .id(2L)
                .title("Category 2")
                .description("Description 2")
                .creationDate(now)
                .updateDate(now)
                .products(new HashSet<>())
                .build();

        ProductCategory category3 = ProductCategory.builder()
                .id(3L)
                .title("Category 3")
                .description("Description 3")
                .creationDate(now)
                .updateDate(now)
                .products(new HashSet<>())
                .build();

        categories = new ArrayList<>(List.of(category1, category2, category3));
    }

    @BeforeEach
    void addResponses() {
        LocalDateTime now = LocalDateTime.now();
        ProductResponse response1 = ProductResponse.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(1_000)
                .updateDate(now)
                .creationDate(now)
                .build();

        ProductResponse response2 = ProductResponse.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(10_000)
                .updateDate(now)
                .creationDate(now)
                .build();

        ProductResponse response3 = ProductResponse.builder()
                .id(3L)
                .name("Product 3")
                .description("Description 3")
                .price(100_000)
                .updateDate(now)
                .creationDate(now)
                .build();

        responses = new ArrayList<>(List.of(response1, response2, response3));
    }

    @Test
    void saveTest() {
        Mockito.when(productCategoryDBService.findById(1L))
                .thenReturn(categories.get(0));
        CreateProductRequest request = new CreateProductRequest("Product 1",
                "Description 1",
                1000, 1L);
        ProductResponse expected = responses.get(0);
        ProductResponse actual = productService.create(null, request);
        LocalDateTime actualDate = actual.getCreationDate();
        LocalDateTime expectedDate = expected.getCreationDate();

        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expectedDate.getHour(), actualDate.getHour());
        Assertions.assertEquals(expectedDate.getMinute(), actualDate.getMinute());
        Assertions.assertEquals(1L, actual.getCategory().getId());
    }

    @Test
    void updateTest() {
        UpdateProductRequest request = new UpdateProductRequest("New product", null, 2);
        Mockito.when(productDBService.findById(1L))
                .thenReturn(products.get(0));

        LocalDateTime now = LocalDateTime.now();
        ProductResponse expected = responses.get(0);
        expected.setName("New product");
        expected.setPrice(2);
        expected.setUpdateDate(now);

        ProductResponse actual = productService.update(null, 1L, request);
        LocalDateTime actualDate = actual.getUpdateDate();

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(now.getHour(), actualDate.getHour());
        Assertions.assertEquals(now.getMinute(), actualDate.getMinute());
    }

    @Test
    void setCategoryTest(){
        Mockito.when(productCategoryDBService.findById(2L))
                .thenReturn(categories.get(1));
        Mockito.when(productDBService.findById(1L))
                .thenReturn(products.get(0));

        ProductResponse actual = productService.setCategory(1L, 2L, null);

        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals(2L, actual.getCategory().getId());
    }

    @Test
    void deleteCategoryTest(){
        Mockito.when(productCategoryDBService.findById(2L))
                .thenReturn(categories.get(1));
        Mockito.when(productDBService.findById(1L))
                .thenReturn(products.get(0));

        ProductResponse actual = productService.deleteCategory(1L, 2L, null);

        Assertions.assertNull(actual.getCategory());
    }
}
