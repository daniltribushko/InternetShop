package org.example.goods.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.goods.models.dto.request.PaymentMethodRequest;
import org.example.goods.models.dto.response.AllPaymentMethodResponse;
import org.example.goods.models.dto.response.PaymentMethodResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Сервис для работы с методами оплаты
 */
public interface PaymentMethodService {
    PaymentMethodResponse create(@Email
                                 @Size(min = 7, max = 50,
                                         message = "Email must be contain from 7 to 50 chars")
                                 String email,
                                 @Valid
                                 PaymentMethodRequest paymentMethodRequest);

    PaymentMethodResponse update(@Email
                                 @Size(min = 7, max = 50,
                                         message = "Email must be contain from 7 to 50 chars")
                                 String email,
                                 @Min(value = 1, message = "Id can not be less than 1")
                                 Long id,
                                 @Valid
                                 PaymentMethodRequest paymentMethodRequest);

    void delete(@Email
                @Size(min = 7, max = 50,
                        message = "Email must be contain from 7 to 50 chars")
                String email,
                @Min(value = 1, message = "Id can not be less than 1")
                Long id);

    AllPaymentMethodResponse findAll(int page,
                                     int per_page,
                                     LocalDateTime creationDate,
                                     LocalDateTime updateDate,
                                     LocalDateTime minCreationDate,
                                     LocalDateTime maxCreationDate,
                                     LocalDateTime minUpdateDate,
                                     LocalDateTime maxUpdateDate);
    PaymentMethodResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                                   Long id);
    Resource getIcon(@Min(value = 1, message = "Id can not be less than 1")
                     Long id);

    PaymentMethodResponse setIcon(@Email
                                  @Size(min = 7, max = 50,
                                          message = "Email must be contain from 7 to 50 chars")
                                  String email,
                                  @Min(value = 1, message = "Id can not be less than 1")
                                  Long id,
                                  MultipartFile icon);
}
