package org.example.goods.service.imp;

import org.example.goods.aspect.annotation.CheckUserBrand;
import org.example.goods.models.dto.request.CreateProductRequest;
import org.example.goods.models.dto.request.UpdateProductRequest;
import org.example.goods.models.dto.response.ProductResponse;
import org.example.goods.models.entities.Product;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.service.ProductService;
import org.example.goods.service.db.ProductCategoryDBService;
import org.example.goods.service.db.ProductDBService;
import org.example.goods.utils.http.date.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 05.06.2024
 * <p>
 * Реализация сервиса по работе с товарами
 */
@Service
public class ProductServiceImp implements ProductService {
    private final ProductDBService productDBService;
    private final ProductCategoryDBService productCategoryDBService;

    @Autowired
    public ProductServiceImp(ProductDBService productDBService,
                             ProductCategoryDBService productCategoryDBService) {
        this.productDBService = productDBService;
        this.productCategoryDBService = productCategoryDBService;
    }

    @Override
    @CheckUserBrand
    public ProductResponse create(String email, CreateProductRequest request) {
        ProductCategory category = productCategoryDBService.findById(request.getCategoryId());
        LocalDateTime now = LocalDateTime.now();
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(category)
                .creationDate(now)
                .brand(request.getBrand())
                .updateDate(now)
                .build();

        Set<Product> products = category.getProducts();
        products.add(product);
        category.setProducts(products);

        productDBService.save(product);
        return ProductResponse.mapFromEntity(product);
    }

    @Override
    @CheckUserBrand
    public ProductResponse update(String email, Long id, UpdateProductRequest request) {
        Product product = productDBService.findById(id);
        String name = request.getName();
        String description = request.getDescription();
        Integer price = request.getPrice();

        if (name != null) {
            product.setName(name);
        }

        if (description != null) {
            product.setDescription(description);
        }

        if (price != null) {
            product.setPrice(price);
        }

        product.setUpdateDate(LocalDateTime.now());

        productDBService.update(product);

        return ProductResponse.mapFromEntity(product);
    }

    @Override
    public void delete(String email, Long id) {
        productDBService.delete(id);
    }

    @Override
    public ProductResponse findById(Long id) {
        return ProductResponse.mapFromEntity(productDBService.findById(id));
    }

    @Override
    public List<ProductResponse> findAll(Long categoryId,
                                         Integer minPrice,
                                         Integer maxPrice,
                                         LocalDateTime creationDate,
                                         LocalDateTime updateDate,
                                         LocalDateTime minCreationDate,
                                         LocalDateTime maxCreationDate,
                                         LocalDateTime minUpdateDate,
                                         LocalDateTime maxUpdateDate) {
        return productDBService.findAll()
                .stream()
                .filter(p -> {
                    ProductCategory category = p.getCategory();
                    return (categoryId == null || category == null ||
                            Objects.equals(category.getId(), categoryId)) &&
                        (minPrice == null || p.getPrice() >= minPrice) &&
                        (maxPrice == null || p.getPrice() <= maxPrice) &&
                        LocalDateTimeUtils.checkDate(p.getCreationDate(),
                                p.getUpdateDate(),
                                creationDate,
                                updateDate,
                                minCreationDate,
                                maxCreationDate,
                                minUpdateDate,
                                maxUpdateDate);
                })
                .map(ProductResponse::mapFromEntity)
                .toList();
    }

    @Override
    @CheckUserBrand
    public ProductResponse setCategory(Long id, Long categoryId, String email) {
        Product product = productDBService.findById(id);
        ProductCategory category = productCategoryDBService.findById(categoryId);

        product.setCategory(category);
        Set<Product> products = category.getProducts();
        products.add(product);
        category.setProducts(products);

        productDBService.update(product);

        return ProductResponse.mapFromEntity(product);
    }

    @Override
    @CheckUserBrand
    public ProductResponse deleteCategory(Long id, Long categoryId, String email) {
        Product product = productDBService.findById(id);
        ProductCategory category = productCategoryDBService.findById(categoryId);

        product.setCategory(null);
        Set<Product> products = category.getProducts();
        products.remove(product);
        category.setProducts(products);

        productDBService.update(product);

        return ProductResponse.mapFromEntity(product);
    }
}
