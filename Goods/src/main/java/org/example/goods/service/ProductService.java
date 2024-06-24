package org.example.goods.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.goods.models.dto.request.CreateProductRequest;
import org.example.goods.models.dto.request.UpdateProductRequest;
import org.example.goods.models.dto.response.AllProductsResponse;
import org.example.goods.models.dto.response.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko danil
 * @since 05.06.2024
 * <p>
 * Сервис для работы с товарами
 */
public interface ProductService {
    ProductResponse create(@Email
                           @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                           String email,
                           @Valid
                           CreateProductRequest request);

    ProductResponse update(@Email
                           @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                           String email,
                           @Min(value = 1, message = "Id can not be less than 1")
                           Long id,
                           @Valid
                           UpdateProductRequest request);

    void delete(@Email
                @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                String email,
                @Min(value = 1, message = "Id can not be less than 1")
                Long id);

    ProductResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                             Long id);

    AllProductsResponse findAll(int page,
                                int per_page,
                                Long categoryId,
                                Integer minPrice,
                                Integer maxPrice,
                                LocalDateTime creationDate,
                                LocalDateTime updateDate,
                                LocalDateTime minCreationDate,
                                LocalDateTime maxCreationDate,
                                LocalDateTime minUpdateDate,
                                LocalDateTime maxUpdateDate);

    ProductResponse setCategory(@Min(value = 1, message = "Id can not be less than 1")
                                Long id,
                                @Min(value = 1, message = "Id can not be less than 1")
                                Long categoryId,
                                @Email
                                @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                                String email);

    ProductResponse deleteCategory(@Min(value = 1, message = "Id can not be less than 1")
                                   Long id,
                                   @Min(value = 1, message = "Id can not be less than 1")
                                   Long categoryId,
                                   @Email
                                   @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                                   String email);
}
