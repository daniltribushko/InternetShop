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
import org.example.goods.models.dto.request.ProductCategoryRequest;
import org.example.goods.models.dto.request.UpdateProductCategoryRequest;
import org.example.goods.models.dto.response.AllProductCategoriesResponse;
import org.example.goods.models.dto.response.ProductCategoryResponse;
import org.example.goods.models.http.ExceptionResponse;
import org.example.goods.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Контроллер для работы с категориями товаров
 */
@RestController
@CrossOrigin
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Product Category Controller")
@RequestMapping("/goods/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @Operation(summary = "Create", description = "Create new product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product category created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryResponse.class))),
            @ApiResponse(responseCode = "409", description = "Product category already exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ProductCategoryResponse> create(Principal principal,
                                                          @Valid
                                                          ProductCategoryRequest productCategoryRequest) {
        ProductCategoryResponse response = productCategoryService.create(principal.getName(),
                productCategoryRequest);

        return ResponseEntity.created(URI.create("/goods/categories/" + response.getId())).body(response);
    }

    @Operation(summary = "Update", description = "Update product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product category updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product category by id or user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> update(@PathVariable
                                                          @Min(value = 1, message = "Id can not be less than 1")
                                                          Long id,
                                                          Principal principal,
                                                          @Valid
                                                          UpdateProductCategoryRequest request) {
        return ResponseEntity.ok(productCategoryService.update(id, principal.getName(), request));
    }

    @Operation(summary = "Delete", description = "Delete product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product category deleted"),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product category by id or user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> delete(@PathVariable
                                                          @Min(value = 1, message = "Id can not be less than 1")
                                                          Long id,
                                                          Principal principal) {
        productCategoryService.delete(id, principal.getName());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find by id", description = "Find product category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product category found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product category by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> findById(@PathVariable
                                                            @Min(value = 1, message = "Id can not be less than 1")
                                                            Long id) {
        return ResponseEntity.ok(productCategoryService.findById(id));
    }

    @Operation(summary = "Find all", description = "Find all product category by params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product categories found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllProductCategoriesResponse.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<AllProductCategoriesResponse> findAll(int page,
                                                                int per_page,
                                                                @RequestParam(required = false)
                                                                Long parentId,
                                                                @RequestParam(required = false)
                                                                Integer minCategoriesSize,
                                                                @RequestParam(required = false)
                                                                Integer maxCategoriesSize,
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
                                                                LocalDateTime maxUpdateDate) {
        return ResponseEntity.ok(productCategoryService.findAll(page, per_page, parentId,
                minCategoriesSize,
                maxCategoriesSize,
                creationDate,
                updateDate,
                minCreationDate,
                maxCreationDate,
                minUpdateDate,
                maxUpdateDate));
    }

    @Operation(summary = "Set parent product category",
            description = "Set parent product category to parent category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent product category set",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parent product category or product category not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{id}/parent/{parentCategoryId}/add")
    public ResponseEntity<ProductCategoryResponse> setPatentProductCategory(@PathVariable
                                                                            @Min(value = 1, message = "Id can not be less than 1")
                                                                            Long id,
                                                                            @PathVariable
                                                                            @Min(value = 1, message = "Parent category id can not be less than 1")
                                                                            Long parentCategoryId,
                                                                            Principal principal) {
        return ResponseEntity.ok(productCategoryService.setParentCategory(id,
                parentCategoryId,
                principal.getName()));
    }

    @Operation(summary = "Delete parent product category",
            description = "Delete parent product category from parent category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent product category deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parent product category or product category not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/{id}/parent/{parentCategoryId}/delete")
    public ResponseEntity<ProductCategoryResponse> deletePatentProductCategory(@PathVariable
                                                                               @Min(value = 1, message = "Id can not be less than 1")
                                                                               Long id,
                                                                               @PathVariable
                                                                               @Min(value = 1, message = "Parent category id can not be less than 1")
                                                                               Long parentCategoryId,
                                                                               Principal principal) {
        return ResponseEntity.ok(productCategoryService.deleteParentCategory(id,
                parentCategoryId,
                principal.getName()));
    }
}
