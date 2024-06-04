package org.example.goods.models.dto.request;

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
    @NotBlank(message = "Title can not be blank")
    private String title;
    @NotBlank(message = "Description can not be blank")
    private String description;
}
