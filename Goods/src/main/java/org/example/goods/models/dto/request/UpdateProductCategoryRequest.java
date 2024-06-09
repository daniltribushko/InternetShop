package org.example.goods.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UpdateProductCategoryRequest {
    @Schema(description = "New product category's title",
            name = "title",
            type = "string",
            example = "New Title")
    private String title;
    @Schema(description = "New description",
            name = "description",
            type = "string",
            example = "New description")
    private String description;
}
