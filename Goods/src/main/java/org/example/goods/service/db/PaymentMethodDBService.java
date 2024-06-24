package org.example.goods.service.db;

import org.example.goods.models.entities.PaymentMethod;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Севис для работы с методами оплаты в бд
 */
public interface PaymentMethodDBService extends DBService<PaymentMethod, Long> {
    boolean existByTitle(String title);
    void update(PaymentMethod entity);
}
