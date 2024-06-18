package org.example.goods.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 05.06.2024
 * <p>
 * Dto запроса на создание товара
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @Schema(description = "Product's name",
            name = "name",
            type = "string",
            example = "Футболка 1")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Schema(description = "Product's description",
            name = "description",
            type = "string",
            example = "Красная футболка")
    @NotBlank(message = "Description can not be blank")
    private String description;

    @Schema(description = "Product's price",
            name = "price",
            type = "integer",
            example = "1000",
            format = "int32")
    @Min(value = 1, message = "Price can not be less than 1")
    private Integer price;

    @Schema(description = "Product's category id",
            name = "categoryId",
            type = "integer",
            example = "1",
            format = "int32")
    @Min(value = 1, message = "Category id can not be less than 1")
    private Long categoryId;
}
