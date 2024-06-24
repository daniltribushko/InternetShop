package org.example.goods.exceptions.categories;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 */
public class ChildCategoryAlreadyExistInCategoryException extends GlobalAppException {
    public ChildCategoryAlreadyExistInCategoryException(Long childCategoryId, Long id) {
        super(409, "Child category with id: " + childCategoryId + " already exist in category with id: " + id);
    }
}
