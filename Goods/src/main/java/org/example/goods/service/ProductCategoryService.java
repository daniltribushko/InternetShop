package org.example.goods.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.goods.models.dto.request.ProductCategoryRequest;
import org.example.goods.models.dto.request.UpdateProductCategoryRequest;
import org.example.goods.models.dto.response.AllProductCategoriesResponse;
import org.example.goods.models.dto.response.ProductCategoryResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Сервис для работы с категориями товаров
 */
public interface ProductCategoryService {
    ProductCategoryResponse create(@Email
                                   @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                                   String email,
                                   @Valid
                                   ProductCategoryRequest request);

    ProductCategoryResponse update(@Min(value = 1, message = "Id can not be less than 1")
                                   Long id,
                                   @Email
                                   @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                                   String email,
                                   UpdateProductCategoryRequest request);

    void delete(@Min(value = 1, message = "Id can not be less than 1")
                Long id,
                @Email
                @Size(min = 7, max = 50, message = "Email must be contain from 7 to 50 chars")
                String email);

    ProductCategoryResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                                     Long id);

    AllProductCategoriesResponse findAll(int page,
                                         int per_page,
                                         Long parentCategoryId,
                                         Integer minCategoriesSize,
                                         Integer maxCategoriesSize,
                                         LocalDateTime creationDate,
                                         LocalDateTime updateDate,
                                         LocalDateTime minCreationDate,
                                         LocalDateTime maxCreationDate,
                                         LocalDateTime minUpdateDate,
                                         LocalDateTime maxUpdateDate);

    ProductCategoryResponse setParentCategory(@Min(value = 1, message = "Id can not be less than 1")
                                              Long id,
                                              @Min(value = 1, message = "Parent category id can not be less than 1")
                                              Long parentCategoryId,
                                              @Email
                                              @Size(min = 7, max = 50,
                                                      message = "Email must be contain from 7 to 50 chars")
                                              String email);

    ProductCategoryResponse deleteParentCategory(@Min(value = 1, message = "Id can not be less than 1")
                                                 Long id,
                                                 @Min(value = 1, message = "Parent category id can not be less than 1")
                                                 Long parentCategoryId,
                                                 @Email
                                                 @Size(min = 7, max = 50,
                                                         message = "Email must be contain from 7 to 50 chars")
                                                 String email);
}
