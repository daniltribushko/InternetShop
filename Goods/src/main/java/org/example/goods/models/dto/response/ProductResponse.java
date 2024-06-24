package org.example.goods.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.goods.models.entities.Product;
import org.example.goods.models.entities.ProductCategory;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 05.06.2024
 * <p>
 * Dto товара
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    @Schema(description = "Product's id",
            name = "id",
            example = "1",
            type = "integer",
            format = "int64")
    private Long id;

    @Schema(description = "Product's name",
            name = "name",
            example = "Футболка 1",
            type = "string")
    private String name;

    @Schema(description = "Product's description",
            name = "description",
            example = "Красная футболка",
            type = "string")
    private String description;

    @Schema(description = "Product's price",
            name = "price",
            type = "integer",
            example = "1000",
            format = "int32")
    private Integer price;

    @Schema(description = "Product's category",
            name = "category",
            type = "object")
    private ProductCategoryResponse category;
    @Schema(description = "Product's brand",
            name = "brand",
            type = "string",
            example = "qwerty")
    private String brand;
    @Schema(description = "Product's creation date",
            name = "creationDate",
            type = "string",
            format = "date-time")
    private LocalDateTime creationDate;

    @Schema(description = "Product's update date",
            name = "updateDate",
            type = "string",
            format = "date-time")
    private LocalDateTime updateDate;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Integer price;
        private ProductCategoryResponse category;
        private String brand;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Integer price) {
            this.price = price;
            return this;
        }

        public Builder category(ProductCategoryResponse category) {
            this.category = category;
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

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public ProductResponse build() {
            ProductResponse productResponse = new ProductResponse();

            productResponse.id = this.id;
            productResponse.name = this.name;
            productResponse.description = this.description;
            productResponse.price = this.price;
            productResponse.category = this.category;
            productResponse.creationDate = this.creationDate;
            productResponse.updateDate = this.updateDate;
            productResponse.brand = this.brand;

            return productResponse;
        }
    }

    public static ProductResponse mapFromEntity(Product product) {
        ProductCategory category = product.getCategory();
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .brand(product.getBrand())
                .category(category != null ?
                        ProductCategoryResponse.mapFromEntityParentCategory(category) : null)
                .creationDate(product.getCreationDate())
                .updateDate(product.getUpdateDate())
                .build();
    }
}
