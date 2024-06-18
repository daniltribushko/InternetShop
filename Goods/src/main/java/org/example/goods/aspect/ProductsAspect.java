package org.example.goods.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.goods.exceptions.products.ProductAlreadyExistInCategoryException;
import org.example.goods.exceptions.products.ProductDontExistInProductCategoryException;
import org.example.goods.exceptions.products.ProductDontHaveCategoryException;
import org.example.goods.models.entities.Product;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.service.db.ProductCategoryDBService;
import org.example.goods.service.db.ProductDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Tribushko danil
 * @since 05.06.2024
 * <p>
 * Аспект для товаров
 */
@Aspect
@Component
public class ProductsAspect {
    private final ProductDBService productDBService;
    private final ProductCategoryDBService productCategoryDBService;

    @Autowired
    public ProductsAspect(ProductDBService productDBService,
                          ProductCategoryDBService productCategoryDBService) {
        this.productDBService = productDBService;
        this.productCategoryDBService = productCategoryDBService;
    }

    @Before(value = "args(id,categoryId,..) && " +
            "execution(public org.example.goods.models.dto.response.ProductResponse " +
            "deleteCategory(Long,Long,..))",
            argNames = "id,categoryId")
    public void checkProductInCategory(Long id, Long categoryId) {
        Product product = productDBService.findById(id);
        ProductCategory productCategory = productCategoryDBService.findById(categoryId);
        ProductCategory categoryCurrentProduct = product.getCategory();
        if (categoryCurrentProduct == null){
            throw new ProductDontHaveCategoryException(id);
        }
        if (!Objects.equals(product.getCategory().getId(), productCategory.getId())){
            throw new ProductDontExistInProductCategoryException(id, categoryId);
        }
    }

    @Before(value = "args(id,categoryId,..) && " +
            "execution(public org.example.goods.models.dto.response.ProductResponse " +
            "setCategory(Long,Long,..))",
    argNames = "id,categoryId")
    public void checkProductAlreadyInCategory(Long id, Long categoryId){
        Product product = productDBService.findById(id);
        ProductCategory productCategory = productCategoryDBService.findById(categoryId);
        if (product.getCategory() != null &&
                Objects.equals(product.getCategory().getId(), productCategory.getId())){
            throw new ProductAlreadyExistInCategoryException(id, categoryId);
        }
    }
}
