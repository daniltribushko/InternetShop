package org.example.goods.service.db;

import org.example.goods.models.entities.Product;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Сервис для работы с товарами
 */
public interface ProductDBService extends DBService<Product, Long> {
    void update(Product product);
    boolean existByNameAndDescription(String name, String description);
}
