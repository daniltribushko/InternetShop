package org.example.goods.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.goods.models.dto.request.DeliveryMethodRequest;
import org.example.goods.models.dto.response.AllDeliveriesMethodsResponse;
import org.example.goods.models.dto.response.DeliveryMethodResponse;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Сервис для работы с методами доставки
 */
public interface DeliveryMethodService {
    DeliveryMethodResponse create(@Email
                                  @Size(min = 7, max = 50,
                                          message = "Email must be contain from 7 to 50 chars")
                                  String email,
                                  @Valid
                                  DeliveryMethodRequest deliveryMethodRequest);

    DeliveryMethodResponse update(@Email
                                  @Size(min = 7, max = 50,
                                          message = "Email must be contain from 7 to 50 chars")
                                  String email,
                                  @Min(value = 1, message = "Id can not be less than 1")
                                  Long id,
                                  @Valid
                                  DeliveryMethodRequest deliveryMethodRequest);

    DeliveryMethodResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                                    Long id);

    void delete(@Email
                @Size(min = 7, max = 50,
                        message = "Email must be contain from 7 to 50 chars")
                String email,
                @Min(value = 1, message = "Id can not be less than 1")
                Long id);

    AllDeliveriesMethodsResponse findAll(int page,
                                         int per_page,
                                         LocalDateTime creationDate,
                                         LocalDateTime updateDate,
                                         LocalDateTime minCreationDate,
                                         LocalDateTime maxCreationDate,
                                         LocalDateTime minUpdateDate,
                                         LocalDateTime maxUpdateDate);
}
