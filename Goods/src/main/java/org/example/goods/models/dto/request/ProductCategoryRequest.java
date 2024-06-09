package org.example.goods.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Dto запроса на работу с категорией товара
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryRequest {
    @Schema(description = "Product category title",
            name = "title",
            type = "string",
            example = "Футболки")
    @NotBlank(message = "Title can not be blank")
    private String title;
    @Schema(description = "Product category description",
            name = "description",
            type = "string",
            example = "Футболки имеются разных размеров")
    @NotBlank(message = "Description can not be blank")
    private String description;
}
