package org.example.goods.models.dto.request;

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
    private String title;
    private String description;
}
