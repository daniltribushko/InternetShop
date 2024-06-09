package org.example.goods.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Сущность товара
 */
@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Integer price;
        private LocalDateTime creationDate;
        private LocalDateTime updateDate;
        private ProductCategory category;

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

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Builder category(ProductCategory category) {
            this.category = category;
            return this;
        }

        public Product build() {
            Product product = new Product();

            product.id = this.id;
            product.name = this.name;
            product.description = this.description;
            product.price = this.price;
            product.creationDate = this.creationDate;
            product.updateDate = this.updateDate;
            product.category = this.category;

            return product;
        }
    }

}
