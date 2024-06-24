package org.example.goods.repositories;

import org.example.goods.models.entities.ProductCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryPaginationRepository extends PagingAndSortingRepository<ProductCategory, Long> {
}
