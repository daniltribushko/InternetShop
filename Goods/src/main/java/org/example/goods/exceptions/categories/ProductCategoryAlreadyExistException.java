package org.example.goods.exceptions.categories;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 */
public class ProductCategoryAlreadyExistException extends GlobalAppException {
    public ProductCategoryAlreadyExistException(String title) {
        super(409, "Product category: " + title + " already exists");
    }
}
