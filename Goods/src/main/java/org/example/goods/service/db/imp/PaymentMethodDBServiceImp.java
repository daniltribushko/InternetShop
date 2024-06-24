package org.example.goods.service.db.imp;

import org.example.goods.exceptions.payments.PaymentMethodAlreadyExistException;
import org.example.goods.exceptions.payments.PaymentMethodByIdNotFoundException;
import org.example.goods.models.entities.PaymentMethod;
import org.example.goods.repositories.PaymentMethodPaginationRepository;
import org.example.goods.repositories.PaymentMethodRepository;
import org.example.goods.service.db.PaymentMethodDBService;
import org.example.goods.utils.files.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tribuhsko Danil
 * @since 24.06.2024
 * <p>
 * Реализация сервиса по работе с методами оплаты
 */
@Service
public class PaymentMethodDBServiceImp implements PaymentMethodDBService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodPaginationRepository paymentMethodPaginationRepository;

    @Value("${methods.deliveries.icon-path}")
    private String iconPath;
    @Autowired
    public PaymentMethodDBServiceImp(PaymentMethodRepository paymentMethodRepository,
                                     PaymentMethodPaginationRepository paymentMethodPaginationRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodPaginationRepository = paymentMethodPaginationRepository;
    }

    @Override
    public boolean existByTitle(String title) {
        return paymentMethodRepository.existsByTitle(title);
    }

    @Override
    public void update(PaymentMethod entity) {
        paymentMethodRepository.save(entity);
    }

    @Override
    public void save(PaymentMethod entity) {
        String title = entity.getTitle();
        if (existByTitle(title)) {
            throw new PaymentMethodAlreadyExistException(title);
        }
        paymentMethodRepository.save(entity);
    }

    @Override
    public PaymentMethod findById(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new PaymentMethodByIdNotFoundException(id));
    }

    @Override
    public Page<PaymentMethod> findAll(Pageable pageable) {
        return paymentMethodPaginationRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        PaymentMethod method = findById(id);
        String fileName = method.getIcon();
        FilesUtil.delete(iconPath, fileName);
        paymentMethodRepository.delete(method);
    }
}
