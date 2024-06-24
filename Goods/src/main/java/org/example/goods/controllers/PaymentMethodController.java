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
import org.example.goods.models.dto.request.PaymentMethodRequest;
import org.example.goods.models.dto.response.PaymentMethodResponse;
import org.example.goods.models.http.ExceptionResponse;
import org.example.goods.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.security.Principal;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Контроллер для работы с методами оплаты
 */
@RestController
@SecurityRequirement(name = "jwtAuth")
@CrossOrigin
@Tag(name = "Payment Method Controller")
@RequestMapping("/methods/payment")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @Operation(summary = "Create", description = "Create payment method, secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment method created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethodResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Payment method already exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PaymentMethodResponse> create(Principal principal,
                                                        @Valid
                                                        @RequestBody
                                                        PaymentMethodRequest paymentMethodRequest) {
        PaymentMethodResponse response = paymentMethodService.create(principal.getName(), paymentMethodRequest);

        return ResponseEntity.created(URI.create("/goods/methods/payment/" + response.getId())).body(response);
    }

    @Operation(summary = "Update", description = "Update payment method, secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment method updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethodResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or payment method by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PaymentMethodResponse> update(Principal principal,
                                                        @PathVariable
                                                        @Min(value = 1, message = "Id can not be less than 1")
                                                        Long id,
                                                        @Valid
                                                        @RequestBody
                                                        PaymentMethodRequest paymentMethodRequest) {
        return ResponseEntity.ok(paymentMethodService.update(principal.getName(), id, paymentMethodRequest));
    }

    @Operation(summary = "Find by id", description = "Find payment method by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment method found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethodResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payment method by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> findById(@PathVariable
                                                          @Min(value = 1, message = " Id can not be less than 1")
                                                          Long id) {
        return ResponseEntity.ok(paymentMethodService.findById(id));
    }

    @Operation(summary = "Set icon", description = "Set icon in payment method, secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Icon set",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethodResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or payment method by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "FileStorage exception",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))})
    @PatchMapping(value = "/{id}/icons/set", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PaymentMethodResponse> setIcon(Principal principal,
                                                         @PathVariable
                                                         @Min(value = 1, message = "Id can not be less than 1")
                                                         Long id,
                                                         @RequestPart("icon")
                                                         MultipartFile icon) {
        return ResponseEntity.ok(paymentMethodService.setIcon(principal.getName(), id, icon));
    }

    @Operation(summary = "Get icon", description = "Get icon from payment method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Icon got",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "Payment method not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "FileStorage exception",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))})
    @GetMapping("/{id}/icons")
    public ResponseEntity<Resource> getIcon(@PathVariable
                                            @Min(value = 1, message = "Id can not be less than 1")
                                            Long id) {
        Resource file = paymentMethodService.getIcon(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", file.getFilename()))
                .body(file);
    }
}
