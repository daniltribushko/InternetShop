package org.example.goods.service.imp;

import org.example.goods.aspect.annotation.CheckUserAdmin;
import org.example.goods.models.dto.request.DeliveryMethodRequest;
import org.example.goods.models.dto.response.AllDeliveriesMethodsResponse;
import org.example.goods.models.dto.response.DeliveryMethodResponse;
import org.example.goods.models.entities.DeliveryMethod;
import org.example.goods.service.DeliveryMethodService;
import org.example.goods.service.db.DeliveryMethodDBService;
import org.example.goods.utils.http.date.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Tribusko Danil
 * @since 23.06.2024
 * <p>
 * Реализация сервиса для работы с методами доставок
 */
@Service
public class DeliveryMethodServiceImp implements DeliveryMethodService {
    private final DeliveryMethodDBService deliveryMethodDBService;

    @Autowired
    public DeliveryMethodServiceImp(DeliveryMethodDBService deliveryMethodDBService) {
        this.deliveryMethodDBService = deliveryMethodDBService;
    }

    @Override
    @CheckUserAdmin
    public DeliveryMethodResponse create(String email,
                                         DeliveryMethodRequest deliveryMethodRequest) {
        LocalDateTime now = LocalDateTime.now();
        DeliveryMethod deliveryMethod = DeliveryMethod.builder()
                .title(deliveryMethodRequest.getTitle())
                .description(deliveryMethodRequest.getDescription())
                .updateDate(now)
                .creationDate(now)
                .build();

        deliveryMethodDBService.save(deliveryMethod);

        return DeliveryMethodResponse.mapFromEntity(deliveryMethod);
    }

    @Override
    @CheckUserAdmin
    public DeliveryMethodResponse update(String email,
                                         Long id,
                                         DeliveryMethodRequest deliveryMethodRequest) {
        DeliveryMethod deliveryMethod = deliveryMethodDBService.findById(id);


        String title = deliveryMethodRequest.getTitle();
        String description = deliveryMethodRequest.getDescription();

        if (title != null) {
            deliveryMethod.setTitle(title);
        }
        if (description != null) {
            deliveryMethod.setDescription(description);
        }
        LocalDateTime now = LocalDateTime.now();
        deliveryMethod.setUpdateDate(now);

        deliveryMethodDBService.update(deliveryMethod);

        return DeliveryMethodResponse.mapFromEntity(deliveryMethod);
    }

    @Override
    public DeliveryMethodResponse findById(Long id) {
        return DeliveryMethodResponse.mapFromEntity(deliveryMethodDBService.findById(id));
    }

    @Override
    @CheckUserAdmin
    public void delete(String email,
                       Long id) {
        deliveryMethodDBService.delete(id);
    }

    @Override
    public AllDeliveriesMethodsResponse findAll(LocalDateTime creationDate,
                                                LocalDateTime updateDate,
                                                LocalDateTime minCreationDate,
                                                LocalDateTime maxCreationDate,
                                                LocalDateTime minUpdateDate,
                                                LocalDateTime maxUpdateDate) {
        return new AllDeliveriesMethodsResponse(deliveryMethodDBService.findAll()
                .stream()
                .filter(o -> (LocalDateTimeUtils.checkDate(o.getCreationDate(),
                        o.getUpdateDate(),
                        creationDate,
                        updateDate,
                        minCreationDate,
                        maxCreationDate,
                        minUpdateDate,
                        maxUpdateDate)))
                .map(DeliveryMethodResponse::mapFromEntity)
                .toList());
    }
}
