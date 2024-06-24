package org.example.goods.models.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.goods.models.entities.PaymentMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Dto способа оплаты
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class PaymentMethodResponse {
    private Long id;
    private String title;
    private String description;
    private String icon;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private static final String iconPath = "icons/";

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

        public PaymentMethodResponse build() {
            PaymentMethodResponse response = new PaymentMethodResponse();
            response.id = this.id;
            response.title = this.title;
            response.description = this.description;
            response.icon = this.icon;
            response.creationDate = this.creationDate;
            response.updateDate = this.updateDate;
            return response;
        }
    }

    public static PaymentMethodResponse mapFromEntity(PaymentMethod paymentMethod) {
        return PaymentMethodResponse.builder()
                .id(paymentMethod.getId())
                .title(paymentMethod.getTitle())
                .description(paymentMethod.getDescription())
                .icon(iconPath + paymentMethod.getIcon())
                .creationDate(paymentMethod.getCreationDate())
                .updateDate(paymentMethod.getUpdateDate())
                .build();
    }
}
