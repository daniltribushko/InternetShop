package org.example.goods.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.goods.models.dto.request.DeliveryMethodRequest;
import org.example.goods.models.dto.response.AllDeliveriesMethodsResponse;
import org.example.goods.models.dto.response.DeliveryMethodResponse;
import org.example.goods.models.http.ExceptionResponse;
import org.example.goods.service.DeliveryMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;


/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Контроллер для работы с методами доставок
 */
@RestController
@CrossOrigin
@Tag(name = "Deliveries Methods Controller")
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/methods/deliveries")
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;

    @Autowired
    public DeliveryMethodController(DeliveryMethodService deliveryMethodService) {
        this.deliveryMethodService = deliveryMethodService;
    }

    @Operation(summary = "Create", description = "Create new delivery method, secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Delivery method created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryMethodResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Delivery method already exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DeliveryMethodResponse> create(Principal principal,
                                                         @Valid
                                                         @RequestBody
                                                         DeliveryMethodRequest deliveryMethodRequest) {
        DeliveryMethodResponse response = deliveryMethodService.create(principal.getName(), deliveryMethodRequest);

        return ResponseEntity.created(URI.create("/goods/methods/deliveries/" + response.getId())).body(response);
    }

    @Operation(summary = "Update", description = "Update delivery method, secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery method updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryMethodResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or delivery method by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DeliveryMethodResponse> updated(Principal principal,
                                                          @PathVariable
                                                          @Min(value = 1, message = "Id can not be less than 1")
                                                          Long id,
                                                          @Valid
                                                          @RequestBody
                                                          DeliveryMethodRequest deliveryMethodRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryMethodService.update(principal.getName(), id, deliveryMethodRequest));
    }

    @Operation(summary = "Find by id", description = "Find delivery method by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery method found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryMethodResponse.class))),
            @ApiResponse(responseCode = "404", description = "Delivery method by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryMethodResponse> findById(@PathVariable
                                                           @Min(value = 1, message = "Id can not be less than 1")
                                                           Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryMethodService.findById(id));
    }

    @Operation(summary = "Delete", description = "Delete delivery method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delivery method deleted"),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or delivery method by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(Principal principal,
                                    @PathVariable
                                    @Min(value = 1, message = "Id can not be less than 1")
                                    Long id) {
        deliveryMethodService.delete(principal.getName(), id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<AllDeliveriesMethodsResponse> findAll(int page,
                                                                int per_page,
                                                                @RequestParam(required = false)
                                                            LocalDateTime creationDate,
                                                                @RequestParam(required = false)
                                                            LocalDateTime updateDate,
                                                                @RequestParam(required = false)
                                                            LocalDateTime minCreationDate,
                                                                @RequestParam(required = false)
                                                            LocalDateTime maxCreationDate,
                                                                @RequestParam(required = false)
                                                            LocalDateTime minUpdateDate,
                                                                @RequestParam(required = false)
                                                            LocalDateTime maxUpdateDate){
        return ResponseEntity.ok(deliveryMethodService.findAll(page, per_page,
                creationDate,
                updateDate,
                minCreationDate,
                maxCreationDate,
                minUpdateDate,
                maxUpdateDate));
    }
}
