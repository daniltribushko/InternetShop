package org.example.goods.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Сущность категории продукта
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products_categories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "parent_category_id")
    private ProductCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<ProductCategory> categories;

    @OneToMany(mappedBy = "category",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<Product> products;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private ProductCategory parentCategory;
        private Set<ProductCategory> categories;
        private Set<Product> products;
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

        public Builder parentCategory(ProductCategory parentCategory) {
            this.parentCategory = parentCategory;
            return this;
        }

        public Builder categories(Set<ProductCategory> categories) {
            this.categories = categories;
            return this;
        }

        public Builder products(Set<Product> products) {
            this.products = products;
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

        public ProductCategory build() {
            ProductCategory productCategory = new ProductCategory();

            productCategory.id = this.id;
            productCategory.title = this.title;
            productCategory.description = this.description;
            productCategory.parentCategory = this.parentCategory;
            productCategory.categories = this.categories;
            productCategory.products = this.products;
            productCategory.creationDate = this.creationDate;
            productCategory.updateDate = this.updateDate;

            return productCategory;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory category = (ProductCategory) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(title, category.title) &&
                Objects.equals(description, category.description) &&
                Objects.equals(parentCategory, category.parentCategory) &&
                Objects.equals(creationDate, category.creationDate) &&
                Objects.equals(updateDate, category.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, parentCategory, creationDate, updateDate);
    }
}
