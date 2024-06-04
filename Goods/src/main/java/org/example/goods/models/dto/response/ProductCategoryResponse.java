package org.example.goods.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.goods.models.entities.ProductCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Dto категории товара
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductCategoryResponse {
    @Schema(description = "Product category id",
            name = "id",
            type = "int64",
            example = "1")
    private Long id;

    @Schema(description = "Product category title",
            name = "title",
            type = "string",
            example = "Футболки")
    private String title;

    @Schema(description = "Product category description",
            name = "description",
            type = "string",
            example = "Футболки имеются разных размеров")
    private String description;

    @Schema(description = "Parent product category",
            name = "parent_category",
            type = "object")
    @JsonProperty(value = "parent_category")
    private ProductCategoryResponse parentCategory;

    @Schema(description = "List child categories",
            name = "categories")
    private List<ProductCategoryResponse> categories;

    @Schema(description = "Product category creation date",
            name = "creationDate",
            type = "string",
            format = "date-time")
    private LocalDateTime creationDate;

    @Schema(description = "Product category update date",
            name = "updateDate",
            type = "string",
            format = "date-time")
    private LocalDateTime updateDate;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private ProductCategoryResponse parentCategory;
        private List<ProductCategoryResponse> categories;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder parentCategory(ProductCategoryResponse parentCategory) {
            this.parentCategory = parentCategory;
            return this;
        }

        public Builder categories(List<ProductCategoryResponse> categories) {
            this.categories = categories;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public ProductCategoryResponse build() {
            ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();

            productCategoryResponse.id = this.id;
            productCategoryResponse.title = this.title;
            productCategoryResponse.description = this.description;
            productCategoryResponse.parentCategory = this.parentCategory;
            productCategoryResponse.categories = this.categories;
            productCategoryResponse.creationDate = this.creationDate;
            productCategoryResponse.updateDate = this.updateDate;

            return productCategoryResponse;
        }
    }

    public static ProductCategoryResponse mapFromEntity(ProductCategory productCategory) {
        ProductCategory parentCategory = productCategory.getParentCategory();
        Set<ProductCategory> categories = productCategory.getCategories();
        
        return ProductCategoryResponse.builder()
                .id(productCategory.getId())
                .title(productCategory.getTitle())
                .description(productCategory.getDescription())
                .categories(categories == null || categories.isEmpty() ? null : categories
                        .stream()
                        .map(ProductCategoryResponse::mapFromEntityWithoutParentCategory)
                        .toList())
                .parentCategory(parentCategory != null ? ProductCategoryResponse
                        .mapFromEntityParentCategory(parentCategory) : null)
                .creationDate(productCategory.getCreationDate())
                .updateDate(productCategory.getUpdateDate())
                .build();
    }
    private static ProductCategoryResponse mapFromEntityParentCategory(ProductCategory productCategory) {
        return ProductCategoryResponse.builder()
                .id(productCategory.getId())
                .title(productCategory.getTitle())
                .description(productCategory.getDescription())
                .creationDate(productCategory.getCreationDate())
                .updateDate(productCategory.getUpdateDate())
                .build();
    }

    private static ProductCategoryResponse mapFromEntityWithoutParentCategory(ProductCategory productCategory) {
        Set<ProductCategory> categories = productCategory.getCategories();
        return ProductCategoryResponse.builder()
                .id(productCategory.getId())
                .title(productCategory.getTitle())
                .description(productCategory.getDescription())
                .categories(categories == null ? null : productCategory.getCategories()
                        .stream()
                        .map(ProductCategoryResponse::mapFromEntityWithoutParentCategory)
                        .toList())
                .creationDate(productCategory.getCreationDate())
                .updateDate(productCategory.getUpdateDate())
                .build();
    }
}
