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
import org.example.goods.models.dto.request.CreateProductRequest;
import org.example.goods.models.dto.request.UpdateProductRequest;
import org.example.goods.models.dto.response.AllProductsResponse;
import org.example.goods.models.dto.response.ProductResponse;
import org.example.goods.models.http.ExceptionResponse;
import org.example.goods.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Контроллер для работы с продуктами
 */
@RestController
@SecurityRequirement(name = "jwtAuth")
@CrossOrigin
@Tag(name = "Goods Controller")
@RequestMapping("/goods")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create", description = "Create new product, secured by brand and admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product create",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not brand or admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product category not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Product already exist exception",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_BRAND"})
    public ResponseEntity<ProductResponse> create(Principal principal,
                                                  @Valid
                                                  @RequestBody
                                                  CreateProductRequest request) {
        ProductResponse response = productService.create(principal.getName(), request);
        return ResponseEntity.created(URI.create("/goods/" + response.getId())).body(response);
    }

    @Operation(summary = "Update", description = "Update product, secured by brand and amin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin or brand",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_BRAND"})
    public ResponseEntity<ProductResponse> update(Principal principal,
                                                  @PathVariable
                                                  @Min(value = 1, message = "Id can not be less than 1")
                                                  Long id,
                                                  @Valid
                                                  @RequestBody
                                                  UpdateProductRequest request) {
        return ResponseEntity.ok(productService.update(principal.getName(), id, request));
    }

    @Operation(summary = "Delete", description = "Delete product, secured by admin and brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "403", description = "User not admin or brand",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_BRAND"})
    public ResponseEntity<ProductResponse> delete(Principal principal,
                                                  @PathVariable
                                                  @Min(value = 1, message = "Id can not be less than 1")
                                                  Long id) {
        productService.delete(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find by id", description = "Find product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<ProductResponse> findById(@PathVariable
                                                    @Min(value = 1, message = "Id can not be less than 1")
                                                    Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Find all", description = "Find all products with params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllProductsResponse.class)))
    })
    @GetMapping("/all")
    @Secured("ROLE_USER")
    public ResponseEntity<AllProductsResponse> findAll(int page,
                                                       int per_page,
                                                       @RequestParam(required = false)
                                                       @Schema(description = "Product's category id",
                                                               name = "categoryId",
                                                               type = "integer",
                                                               format = "int64")
                                                       Long categoryId,
                                                       @Schema(description = "Min price for products",
                                                               name = "min price",
                                                               type = "integer",
                                                               format = "int32"
                                                       )
                                                       @RequestParam(required = false)
                                                       Integer minPrice,
                                                       @Schema(description = "Max price for products",
                                                               name = "maxPrice",
                                                               type = "integer",
                                                               format = "int32")
                                                       @RequestParam(required = false)
                                                       Integer maxPrice,
                                                       @Schema(description = "Product's creation date",
                                                               name = "creationDate",
                                                               type = "string",
                                                               format = "date-time")
                                                       @RequestParam(required = false)
                                                       LocalDateTime creationDate,
                                                       @Schema(description = "Product's update date",
                                                               name = "updateDate",
                                                               type = "string",
                                                               format = "date-time")
                                                       @RequestParam(required = false)
                                                       LocalDateTime updateDate,
                                                       @Schema(description = "Minimum creation date of product",
                                                               name = "minCreationDate",
                                                               type = "string",
                                                               format = "date-time")
                                                       @RequestParam(required = false)
                                                       LocalDateTime minCreationDate,
                                                       @Schema(description = "Maximum creation date of product",
                                                               name = "maxCreationDate",
                                                               type = "string",
                                                               format = "date-time")
                                                       @RequestParam(required = false)
                                                       LocalDateTime maxCreationDate,
                                                       @Schema(description = "Minimum update date of product",
                                                               name = "minUpdateDate",
                                                               type = "string",
                                                               format = "date-time")
                                                       @RequestParam(required = false)
                                                       LocalDateTime minUpdateDate,
                                                       @Schema(description = "Maximum update date of product",
                                                               name = "maxUpdateDate",
                                                               type = "string",
                                                               format = "date-time")
                                                       @RequestParam(required = false)
                                                       LocalDateTime maxUpdateDate) {
        return ResponseEntity.ok(productService.findAll(page, per_page, categoryId,
                minPrice,
                maxPrice,
                creationDate,
                updateDate,
                minCreationDate,
                maxCreationDate,
                minUpdateDate,
                maxUpdateDate));
    }

    @Operation(summary = "Set category", description = "Set product category to product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product category set",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product or product category by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin or brand",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Product already exist in exception",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{id}/categories/{categoryId}/add")
    public ResponseEntity<ProductResponse> setCategory(Principal principal,
                                                       @PathVariable
                                                       @Min(value = 1, message = "Id can not be less than 1")
                                                       Long id,
                                                       @PathVariable
                                                       @Min(value = 1, message = "Id can not be less than 1")
                                                       Long categoryId) {
        return ResponseEntity.ok(productService.setCategory(id, categoryId, principal.getName()));
    }

    @Operation(summary = "Delete category", description = "Delete category from product, secured by admin and brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product category deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product or product category by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin or brand",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Product not found in category",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{id}/categories/{categoryId}/delete")
    public ResponseEntity<ProductResponse> deleteCategory(Principal principal,
                                                          @PathVariable
                                                          @Min(value = 1, message = "Id can not be less than 1")
                                                          Long id,
                                                          @PathVariable
                                                          @Min(value = 1, message = "Id can not be less than 1")
                                                          Long categoryId) {
        return ResponseEntity.ok(productService.deleteCategory(id,
                categoryId,
                principal.getName()));
    }
}
