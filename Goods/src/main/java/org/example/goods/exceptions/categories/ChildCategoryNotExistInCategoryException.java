package org.example.goods.exceptions.categories;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 */
public class ChildCategoryNotExistInCategoryException extends GlobalAppException {
    public ChildCategoryNotExistInCategoryException(Long childCategoryId, Long id) {
        super(404, "Child category with id: " + childCategoryId + " not exist in category with id: " + id);
    }
}
