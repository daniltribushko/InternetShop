package org.example.goods.repositories;

import org.example.goods.models.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Репозиторий для работы с ущностаями категорий продуктов
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    boolean existsByTitle(String title);
}
