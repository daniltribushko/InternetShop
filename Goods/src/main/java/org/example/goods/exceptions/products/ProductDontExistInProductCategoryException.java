package org.example.goods.exceptions.products;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 05.06.2024
 */
public class ProductDontExistInProductCategoryException extends GlobalAppException {
    public ProductDontExistInProductCategoryException(Long id, Long categoryId) {
        super(409, "Product with id " + id + " does not exist in product category " + categoryId);
    }
}
