package org.example.goods.exceptions.deliveries;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 23.06.2024
 */
public class DeliveryMethodAlreadyExistException extends GlobalAppException {
    public DeliveryMethodAlreadyExistException(String title) {
        super(409, "Delivery method: " + title + " already exist");
    }
}
