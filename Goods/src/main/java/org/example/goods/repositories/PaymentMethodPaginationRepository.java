package org.example.goods.repositories;

import org.example.goods.models.entities.PaymentMethod;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodPaginationRepository extends PagingAndSortingRepository<PaymentMethod, Long> {
}
