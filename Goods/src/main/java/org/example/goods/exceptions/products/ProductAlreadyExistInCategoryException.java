package org.example.goods.exceptions.products;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribusko Danil
 * @since 07.06.2024
 */
public class ProductAlreadyExistInCategoryException extends GlobalAppException {
    public ProductAlreadyExistInCategoryException(Long id, Long categoryId) {
        super(409, "Product with id: " + id + " already exist in category with id: " + categoryId);
    }
}
