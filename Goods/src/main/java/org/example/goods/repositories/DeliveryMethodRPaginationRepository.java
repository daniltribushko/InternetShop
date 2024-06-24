package org.example.goods.repositories;

import org.example.goods.models.entities.DeliveryMethod;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodRPaginationRepository extends PagingAndSortingRepository<DeliveryMethod, Long> {
}
