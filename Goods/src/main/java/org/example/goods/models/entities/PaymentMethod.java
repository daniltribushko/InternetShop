package org.example.goods.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Сущность метода доставки
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payments_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "icon")
    private String icon;
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
        private String icon;
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

        public Builder icon(String icon) {
            this.icon = icon;
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

        public PaymentMethod build() {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.id = this.id;
            paymentMethod.title = this.title;
            paymentMethod.description = this.description;
            paymentMethod.icon = this.icon;
            paymentMethod.creationDate = this.creationDate;
            paymentMethod.updateDate = this.updateDate;

            return paymentMethod;
        }
    }
}
