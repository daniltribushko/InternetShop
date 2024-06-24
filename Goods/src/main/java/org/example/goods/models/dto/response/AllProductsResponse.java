package org.example.goods.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Tribushko Danil
 * @since 07.06.2024
 * <p>
 * Dto несколких продуктов
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllProductsResponse {
    private long total;
    private int page;
    private int per_page;
    private List<ProductResponse> products;
}
