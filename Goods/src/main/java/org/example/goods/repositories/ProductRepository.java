package org.example.goods.repositories;

import org.example.goods.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Репозиторий для работы с товарами
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndDescription(String name, String description);
}
