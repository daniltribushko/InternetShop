package org.example.goods.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 *
 * Сущность способа доставки
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "delivaries_methods")
public class DeliveryMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(nullable = false, name = "description")
    private String description;
    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;
    @Column(nullable = false, name = "update_date")
    private LocalDateTime updateDate;

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Long id;
        private String title;
        private String description;
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

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public DeliveryMethod build() {
            DeliveryMethod method = new DeliveryMethod();
            method.id = this.id;
            method.title = this.title;
            method.description = this.description;
            method.creationDate = this.creationDate;
            method.updateDate = this.updateDate;

            return method;
        }
    }
}
