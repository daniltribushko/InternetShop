package org.example.goods.service.db.imp;

import org.example.goods.exceptions.deliveries.DeliveryMethodAlreadyExistException;
import org.example.goods.exceptions.deliveries.DeliveryMethodByIdNotFoundException;
import org.example.goods.models.entities.DeliveryMethod;
import org.example.goods.repositories.DeliveryMethodRPaginationRepository;
import org.example.goods.repositories.DeliveryMethodRepository;
import org.example.goods.service.db.DeliveryMethodDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 *
 * Реализация сервиса для работы с методами доставки товара
 */
@Service
public class DeliveryMethodDBServiceImp implements DeliveryMethodDBService {
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final DeliveryMethodRPaginationRepository paginationRepository;

    @Autowired
    public DeliveryMethodDBServiceImp(DeliveryMethodRepository deliveryMethodRepository,
                                      DeliveryMethodRPaginationRepository paginationRepository) {
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.paginationRepository = paginationRepository;
    }

    @Override
    public void update(DeliveryMethod deliveryMethod) {
        deliveryMethodRepository.save(deliveryMethod);
    }

    @Override
    public boolean existByTitle(String title) {
        return deliveryMethodRepository.existsByTitle(title);
    }

    @Override
    public void save(DeliveryMethod entity) {
        String title = entity.getTitle();
        if (existByTitle(title)) {
            throw new DeliveryMethodAlreadyExistException(title);
        }
        deliveryMethodRepository.save(entity);
    }

    @Override
    public DeliveryMethod findById(Long id) {
        return deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new DeliveryMethodByIdNotFoundException(id));
    }

    @Override
    public Page<DeliveryMethod> findAll(Pageable pageable) {
        return paginationRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        DeliveryMethod deliveryMethod = findById(id);
        deliveryMethodRepository.delete(deliveryMethod);
    }
}
