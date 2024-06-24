package org.example.goods.repositories;

import org.example.goods.models.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Репозиторий для работы с методами оплаты
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    boolean existsByTitle(String title);
}
