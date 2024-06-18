package org.example.goods.exceptions.products;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 */
public class ProductByIdNotFoundException extends GlobalAppException {
    public ProductByIdNotFoundException(Long id) {
        super(404, "Product with id " + id + " not found" );
    }
}
