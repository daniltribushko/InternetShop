package org.example.goods.repositories;

import org.example.goods.models.entities.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Репозиторий для работы с спообами доставки
 */
@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
    boolean existsByTitle(String title);
}
