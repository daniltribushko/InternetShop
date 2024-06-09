package org.example.goods.exceptions.products;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribusko Danil
 * @since 03.06.2024
 */
public class ProductAlreadyExistException extends GlobalAppException {
    public ProductAlreadyExistException(String name, String description) {
        super(409, "Product: " + name + " with description: " + description + " already exists");
    }
}
