package org.example.goods.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Dto нескольких методов доставки
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllDeliveriesMethodsResponse {
    private long total;
    private int page;
    private int per_page;
    private List<DeliveryMethodResponse> deliveryMethods;
}
