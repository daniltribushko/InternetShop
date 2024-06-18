package org.example.goods.exceptions.products;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 07.06.2024
 */
public class ProductDontHaveCategoryException extends GlobalAppException {
    public ProductDontHaveCategoryException(Long id) {
        super(409, "Product with id: " + id + " don't have a product category");
    }
}
