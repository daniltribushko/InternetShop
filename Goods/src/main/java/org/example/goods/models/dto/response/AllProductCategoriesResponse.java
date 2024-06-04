package org.example.goods.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Dto нескольки категорий товаров
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllProductCategoriesResponse {
    @Schema(description = "List of product categories",
            name = "categories")
    private List<ProductCategoryResponse> categories;
}
