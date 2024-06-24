package org.example.goods.service.imp;

import org.example.goods.aspect.annotation.CheckUserAdmin;
import org.example.goods.models.dto.request.PaymentMethodRequest;
import org.example.goods.models.dto.response.AllPaymentMethodResponse;
import org.example.goods.models.dto.response.PaymentMethodResponse;
import org.example.goods.models.entities.PaymentMethod;
import org.example.goods.repositories.PaymentMethodRepository;
import org.example.goods.service.PaymentMethodService;
import org.example.goods.service.db.PaymentMethodDBService;
import org.example.goods.utils.date.LocalDateTimeUtils;
import org.example.goods.utils.files.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Реализация сервиса для работы с методами оплаты
 */
@Service
public class PaymentMethodServiceImp implements PaymentMethodService {
    private final PaymentMethodDBService paymentMethodDBService;
    @Value("${methods.deliveries.icon-path}")
    private String iconPath;

    @Autowired
    public PaymentMethodServiceImp(PaymentMethodDBService paymentMethodDBService) {
        this.paymentMethodDBService = paymentMethodDBService;
    }

    @Override
    @CheckUserAdmin
    public PaymentMethodResponse create(String email, PaymentMethodRequest paymentMethodRequest) {
        LocalDateTime now = LocalDateTime.now();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .title(paymentMethodRequest.getTitle())
                .description(paymentMethodRequest.getDescription())
                .creationDate(now)
                .updateDate(now)
                .build();

        paymentMethodDBService.save(paymentMethod);

        return PaymentMethodResponse.mapFromEntity(paymentMethod);
    }

    @Override
    @CheckUserAdmin
    public PaymentMethodResponse update(String email, Long id, PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodDBService.findById(id);
        String title = paymentMethodRequest.getTitle();
        String description = paymentMethodRequest.getDescription();

        LocalDateTime now = LocalDateTime.now();
        if (title != null) {
            paymentMethod.setTitle(title);
        }
        if (description != null) {
            paymentMethod.setDescription(description);
        }

        paymentMethod.setUpdateDate(now);
        paymentMethodDBService.update(paymentMethod);

        return PaymentMethodResponse.mapFromEntity(paymentMethod);
    }

    @Override
    @CheckUserAdmin
    public void delete(String email, Long id) {
        paymentMethodDBService.delete(id);
    }

    @Override
    public AllPaymentMethodResponse findAll(int page,
                                            int per_page,
                                            LocalDateTime creationDate,
                                            LocalDateTime updateDate,
                                            LocalDateTime minCreationDate,
                                            LocalDateTime maxCreationDate,
                                            LocalDateTime minUpdateDate,
                                            LocalDateTime maxUpdateDate) {
        Page<PaymentMethod> methods = paymentMethodDBService.findAll(PageRequest.of(page, per_page));
        return new AllPaymentMethodResponse(methods.getTotalElements(),
                page,
                per_page,
                methods
                        .stream()
                        .filter(o -> LocalDateTimeUtils.checkDate(o.getCreationDate(),
                                o.getUpdateDate(),
                                creationDate,
                                updateDate,
                                minCreationDate,
                                maxCreationDate,
                                minUpdateDate,
                                maxUpdateDate))
                        .map(PaymentMethodResponse::mapFromEntity)
                        .toList());
    }

    @Override
    public PaymentMethodResponse findById(Long id) {
        return PaymentMethodResponse.mapFromEntity(paymentMethodDBService.findById(id));
    }

    @Override
    public Resource getIcon(Long id) {
        PaymentMethod paymentMethod = paymentMethodDBService.findById(id);
        return FilesUtil.getResource(iconPath, paymentMethod.getIcon());
    }

    @Override
    @CheckUserAdmin
    public PaymentMethodResponse setIcon(String email, Long id, MultipartFile icon) {
        PaymentMethod paymentMethod = paymentMethodDBService.findById(id);
        FilesUtil.saveFile("icon_payment_method_" + paymentMethod.getId(),
                iconPath,
                icon);

        paymentMethod.setUpdateDate(LocalDateTime.now());
        paymentMethodDBService.update(paymentMethod);

        return PaymentMethodResponse.mapFromEntity(paymentMethod);
    }
}
