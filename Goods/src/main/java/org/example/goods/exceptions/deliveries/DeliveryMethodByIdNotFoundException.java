package org.example.goods.exceptions.deliveries;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 */
public class DeliveryMethodByIdNotFoundException extends GlobalAppException {
    public DeliveryMethodByIdNotFoundException(Long id) {
        super(404, "Delivery method with id " + id + " not found");
    }
}
