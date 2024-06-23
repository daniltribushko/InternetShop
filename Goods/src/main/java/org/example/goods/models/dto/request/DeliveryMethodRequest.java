package org.example.goods.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Dto запроса на работу с методом доставки
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodRequest {
    @Schema(description = "Delivery method's title",
            name = "title",
            example = "Delivery Method",
            type = "string")
    private String title;
    @Schema(description = "Delivery method's description",
            name = "description",
            example = "Description",
            type = "string")
    private String description;
}
