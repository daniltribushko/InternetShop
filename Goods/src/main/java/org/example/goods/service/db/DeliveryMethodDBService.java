package org.example.goods.service.db;

import org.example.goods.models.entities.DeliveryMethod;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Сервис для работы с методами доставки товара
 */
public interface DeliveryMethodDBService extends DBService<DeliveryMethod, Long> {
    void update(DeliveryMethod deliveryMethod);
    boolean existByTitle(String title);
}
