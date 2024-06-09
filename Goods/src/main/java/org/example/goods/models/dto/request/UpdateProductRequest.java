package org.example.goods.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 05.06.2024
 * <p>
 * Dto запроса на обновление товара
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    @Schema(description = "Product's name",
            name = "name",
            type = "string",
            example = "Футболка 1")
    private String name;
    @Schema(description = "Product's description",
            name = "description",
            type = "string",
            example = "Красная футболка")
    private String description;
    @Schema(description = "Product's price",
            name = "price",
            type = "integer",
            example = "1000",
            format = "int32")
    @Min(value = 1, message = "Price can not be less than 1")
    private Integer price;
}
