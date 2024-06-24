package org.example.goods.exceptions.categories;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 */
public class ProductCategoryByIdNotFoundException extends GlobalAppException {
    public ProductCategoryByIdNotFoundException(Long id) {
        super(404, "Product category with id " + id + " not found");
    }
}
