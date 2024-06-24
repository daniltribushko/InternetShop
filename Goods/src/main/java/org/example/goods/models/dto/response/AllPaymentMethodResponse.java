package org.example.goods.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Dto несколких методов оплаты
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllPaymentMethodResponse {
    private List<PaymentMethodResponse> paymentMethods;
}
