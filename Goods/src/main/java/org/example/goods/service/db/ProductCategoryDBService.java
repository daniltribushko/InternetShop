package org.example.goods.service.db;

import org.example.goods.models.entities.ProductCategory;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Сервис для работы с категориями товаров в бд
 */
public interface ProductCategoryDBService extends DBService<ProductCategory, Long> {
    void updateProductCategory(ProductCategory productCategory);
    boolean existByTitle(String title);
}
