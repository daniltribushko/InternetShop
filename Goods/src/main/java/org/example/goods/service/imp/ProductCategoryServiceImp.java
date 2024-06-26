package org.example.goods.service.imp;

import org.example.goods.aspect.annotation.CheckUserAdmin;
import org.example.goods.models.dto.request.ProductCategoryRequest;
import org.example.goods.models.dto.request.UpdateProductCategoryRequest;
import org.example.goods.models.dto.response.AllProductCategoriesResponse;
import org.example.goods.models.dto.response.AllProductsResponse;
import org.example.goods.models.dto.response.ProductCategoryResponse;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.service.ProductCategoryService;
import org.example.goods.service.db.ProductCategoryDBService;
import org.example.goods.utils.date.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Реализация сервиса для работы с категориями товаров
 */
@Service
public class ProductCategoryServiceImp implements ProductCategoryService {
    private final ProductCategoryDBService productCategoryDBService;

    @Autowired
    public ProductCategoryServiceImp(ProductCategoryDBService productCategoryDBService) {
        this.productCategoryDBService = productCategoryDBService;
    }

    @Override
    @CheckUserAdmin
    public ProductCategoryResponse create(String email, ProductCategoryRequest request) {
        ProductCategory productCategory = ProductCategory
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        productCategoryDBService.save(productCategory);

        return ProductCategoryResponse.mapFromEntity(productCategory);
    }

    @Override
    @CheckUserAdmin
    public ProductCategoryResponse update(Long id, String email, UpdateProductCategoryRequest request) {
        ProductCategory productCategory = productCategoryDBService.findById(id);
        String title = request.getTitle();
        String description = request.getDescription();

        if (title != null) {
            productCategory.setTitle(title);
        }
        if (description != null) {
            productCategory.setDescription(description);
        }
        productCategory.setUpdateDate(LocalDateTime.now());
        productCategoryDBService.updateProductCategory(productCategory);

        return ProductCategoryResponse.mapFromEntity(productCategory);
    }

    @Override
    @CheckUserAdmin
    public void delete(Long id, String email) {
        productCategoryDBService.delete(id);
    }

    @Override
    public ProductCategoryResponse findById(Long id) {
        return ProductCategoryResponse.mapFromEntity(productCategoryDBService.findById(id));
    }

    @Override
    public AllProductCategoriesResponse findAll(int page,
                                                         int per_page,
                                                         Long parentCategoryId,
                                                         Integer minCategoriesSize,
                                                         Integer maxCategoriesSize,
                                                         LocalDateTime creationDate,
                                                         LocalDateTime updateDate,
                                                         LocalDateTime minCreationDate,
                                                         LocalDateTime maxCreationDate,
                                                         LocalDateTime minUpdateDate,
                                                         LocalDateTime maxUpdateDate) {
        Page<ProductCategory> products = productCategoryDBService.findAll(PageRequest.of(page, per_page));
        return new AllProductCategoriesResponse(products.getTotalPages(),page, per_page,
                products.stream().filter(c -> {
                    int categoriesSize = c.getCategories().isEmpty() ? 0 : c.getCategories().size();
                    return parentCategoryId == null ||
                            Objects.equals(parentCategoryId, c.getParentCategory().getId()) &&
                                    (maxCategoriesSize == null || (categoriesSize < maxCategoriesSize)) &&
                                    (minCategoriesSize == null || (categoriesSize > minCategoriesSize)) &&
                                    LocalDateTimeUtils.checkDate(c.getCreationDate(), c.getUpdateDate(), creationDate,
                                            updateDate,
                                            minCreationDate,
                                            maxCreationDate,
                                            minUpdateDate,
                                            maxUpdateDate);
                })
                .map(ProductCategoryResponse::mapFromEntity)
                .toList());
    }

    @Override
    @CheckUserAdmin
    public ProductCategoryResponse setParentCategory(Long id,
                                                     Long parentCategoryId,
                                                     String email) {
        ProductCategory productCategory = productCategoryDBService.findById(id);

        ProductCategory parentCategory = productCategoryDBService.findById(parentCategoryId);

        productCategory.setParentCategory(parentCategory);
        Set<ProductCategory> categories = parentCategory.getCategories();
        categories.add(productCategory);
        parentCategory.setCategories(categories);

        LocalDateTime now = LocalDateTime.now();
        parentCategory.setUpdateDate(now);
        productCategory.setUpdateDate(now);

        productCategoryDBService.updateProductCategory(productCategory);

        return ProductCategoryResponse.mapFromEntity(productCategory);
    }

    @Override
    @CheckUserAdmin
    public ProductCategoryResponse deleteParentCategory(Long id,
                                                        Long parentCategoryId,
                                                        String email) {
        ProductCategory productCategory = productCategoryDBService.findById(id);
        ProductCategory parentCategory = productCategoryDBService.findById(parentCategoryId);
        productCategory.setParentCategory(null);
        Set<ProductCategory> categories = parentCategory.getCategories();
        categories.remove(productCategory);
        parentCategory.setCategories(categories);

        LocalDateTime now = LocalDateTime.now();
        parentCategory.setUpdateDate(now);
        productCategory.setUpdateDate(now);

        productCategoryDBService.updateProductCategory(parentCategory);

        return ProductCategoryResponse.mapFromEntity(productCategory);
    }
}
