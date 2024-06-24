package org.example.goods.repositories;

import org.example.goods.models.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaginationRepository extends PagingAndSortingRepository<Product, Long> {
}
