package org.example.goods.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.goods.exceptions.categories.ChildCategoryAlreadyExistInCategoryException;
import org.example.goods.exceptions.categories.ChildCategoryNotExistInCategoryException;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.service.db.ProductCategoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Аспект для категорий товаров
 */
@Aspect
@Component
public class ProductCategoryAspect {
    private final ProductCategoryDBService productCategoryDBService;

    @Autowired
    public ProductCategoryAspect(ProductCategoryDBService productCategoryDBService) {
        this.productCategoryDBService = productCategoryDBService;
    }

    @Before(value = "execution(public org.example.goods.models.dto.response.ProductCategoryResponse " +
            "setParentCategory(Long,Long,..)) && args(id,parentId,..)",
            argNames = "id,parentId")
    public void checkProductCategoryDontExistInCategory(Long id, Long parentId) {
        ProductCategory category = productCategoryDBService.findById(id);
        ProductCategory parentCategory = productCategoryDBService.findById(parentId);
        if (parentCategory.getCategories().contains(category)) {
            throw new ChildCategoryAlreadyExistInCategoryException(id, parentId);
        }
    }

    @Before(value = "execution(public org.example.goods.models.dto.response.ProductCategoryResponse " +
            "deleteParentCategory(Long,Long,..)) && args(id,parentId,..)",
            argNames = "id,parentId")
    public void checkProductCategoryExistInCategory(Long id, Long parentId) {
        ProductCategory category = productCategoryDBService.findById(id);
        ProductCategory parentCategory = productCategoryDBService.findById(parentId);
        if (!parentCategory.getCategories().contains(category)) {
            throw new ChildCategoryNotExistInCategoryException(id, parentId);
        }
    }
}
