package org.example.goods.services;

import org.example.goods.models.dto.request.CreateProductRequest;
import org.example.goods.models.dto.request.ProductCategoryRequest;
import org.example.goods.models.dto.request.UpdateProductCategoryRequest;
import org.example.goods.models.dto.response.ProductCategoryResponse;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.service.db.ProductCategoryDBService;
import org.example.goods.service.imp.ProductCategoryServiceImp;
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
class ProductCategoryServiceTest {
    @Mock
    private ProductCategoryDBService productCategoryDBService;
    @InjectMocks
    private ProductCategoryServiceImp productCategoryService;

    private List<ProductCategory> categories;
    private List<ProductCategoryResponse> responses;
    private LocalDateTime now;

    @BeforeEach
    void addCategories(){
        now = LocalDateTime.now();
        ProductCategory category1 = ProductCategory.builder()
                .id(1L)
                .title("Category 1")
                .description("Description 1")
                .creationDate(now)
                .updateDate(now)
                .products(new HashSet<>())
                .categories(new HashSet<>())
                .build();

        ProductCategory category2 = ProductCategory.builder()
                .id(2L)
                .title("Category 2")
                .description("Description 2")
                .creationDate(now)
                .updateDate(now)
                .products(new HashSet<>())
                .categories(new HashSet<>())
                .build();

        ProductCategory category3 = ProductCategory.builder()
                .id(3L)
                .title("Category 3")
                .description("Description 3")
                .creationDate(now)
                .updateDate(now)
                .products(new HashSet<>())
                .categories(new HashSet<>())
                .build();

        categories = new ArrayList<>(List.of(category1, category2, category3));
    }

    @BeforeEach
    void addResponses(){
        ProductCategoryResponse response1 = ProductCategoryResponse.builder()
                .id(1L)
                .title("Category 1")
                .description("Description 1")
                .creationDate(now)
                .updateDate(now)
                .build();

        ProductCategoryResponse response2 = ProductCategoryResponse.builder()
                .id(2L)
                .title("Category 2")
                .description("Description 2")
                .creationDate(now)
                .updateDate(now)
                .build();

        ProductCategoryResponse response3 = ProductCategoryResponse.builder()
                .id(3L)
                .title("Category 3")
                .description("Description 3")
                .creationDate(now)
                .updateDate(now)
                .build();

        responses = new ArrayList<>(List.of(response1, response2, response3));
    }

    @Test
    void saveTest(){
        ProductCategoryRequest request = new ProductCategoryRequest("Category 1", "Description 1");

        ProductCategoryResponse actual = productCategoryService.create(null, request);
        ProductCategoryResponse expected = responses.get(0);

        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void updateTest(){
        Mockito.when(productCategoryDBService.findById(1L))
                .thenReturn(categories.get(0));

        UpdateProductCategoryRequest request = new UpdateProductCategoryRequest("New Category",
                null);
        ProductCategoryResponse actual = productCategoryService.update(1L, null, request);
        LocalDateTime expectedUpdateDate = LocalDateTime.now();
        LocalDateTime actualUpdateDate = actual.getUpdateDate();

        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals("New Category", actual.getTitle());
        Assertions.assertEquals("Description 1", actual.getDescription());
        Assertions.assertEquals(expectedUpdateDate.getHour(), actualUpdateDate.getHour());
        Assertions.assertEquals(expectedUpdateDate.getMinute(), actualUpdateDate.getMinute());
    }

    @Test
    void findByIdTest(){
        Mockito.when(productCategoryDBService.findById(3L))
                .thenReturn(categories.get(2));
        Mockito.when(productCategoryDBService.findById(1L))
                .thenReturn(categories.get(0));

        ProductCategoryResponse actual1 = productCategoryService.findById(1L);
        ProductCategoryResponse actual2 = productCategoryService.findById(3L);

        ProductCategoryResponse expected1 = responses.get(0);
        ProductCategoryResponse expected2 = responses.get(2);

        Assertions.assertEquals(expected1.getId(), actual1.getId());
        Assertions.assertEquals(expected1.getTitle(), actual1.getTitle());
        Assertions.assertEquals(expected1.getDescription(), actual1.getDescription());

        Assertions.assertEquals(expected2.getId(), actual2.getId());
        Assertions.assertEquals(expected2.getTitle(), actual2.getTitle());
        Assertions.assertEquals(expected2.getDescription(), actual2.getDescription());
    }

    @Test
    void setParentCategoryTest(){
        Mockito.when(productCategoryDBService.findById(1L))
                .thenReturn(categories.get(0));
        Mockito.when(productCategoryDBService.findById(2L))
                .thenReturn(categories.get(1));

        ProductCategoryResponse actual1 = productCategoryService.setParentCategory(1L,
                2L, null);

        ProductCategoryResponse parentCategory = actual1.getParentCategory();

        Assertions.assertEquals(1L, actual1.getId());
        Assertions.assertNotNull(parentCategory);
        Assertions.assertEquals(2L, parentCategory.getId());
    }

    @Test
    void deleteParentCategoryTest(){
        ProductCategory category1 = categories.get(0);
        ProductCategory category2 = categories.get(1);
        category1.setParentCategory(category2);

        Mockito.when(productCategoryDBService.findById(1L))
                .thenReturn(category1);
        Mockito.when(productCategoryDBService.findById(2L))
                .thenReturn(category2);

        ProductCategoryResponse actual = productCategoryService.deleteParentCategory(1L,
                2L,
                null);

        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertNull(actual.getParentCategory());
    }
}
