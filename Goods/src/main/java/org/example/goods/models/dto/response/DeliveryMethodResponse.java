package org.example.goods.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.goods.models.entities.DeliveryMethod;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 * <p>
 * Dto метода доставки
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodResponse {
    @Schema(description = "Delivery method's id",
            name = "id",
            type = "integer",
            example = "1",
            format = "int64")
    private Long id;
    @Schema(description = "Delivery method's title",
            name = "title",
            example = "Delivery Method",
            type = "string")
    private String title;
    @Schema(description = "Delivery method's description",
            name = "description",
            example = "Description",
            type = "string")
    private String description;
    @Schema(description = "Delivery method's creation date",
            name = "creationDate",
            type = "string",
            format = "date-time")
    private LocalDateTime creationDate;
    @Schema(description = "Delivery method's update date",
            name = "updateDate",
            type = "string",
            format = "date-time")
    private LocalDateTime updateDate;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
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

        public DeliveryMethodResponse build(){
            DeliveryMethodResponse response = new DeliveryMethodResponse();
            response.id = this.id;
            response.title = this.title;
            response.description = this.description;
            response.creationDate = this.creationDate;
            response.updateDate = this.updateDate;

            return response;
        }
    }

    public static DeliveryMethodResponse mapFromEntity(DeliveryMethod deliveryMethod) {
        return DeliveryMethodResponse.builder()
                .id(deliveryMethod.getId())
                .title(deliveryMethod.getTitle())
                .description(deliveryMethod.getDescription())
                .creationDate(deliveryMethod.getCreationDate())
                .updateDate(deliveryMethod.getUpdateDate())
                .build();
    }
}
