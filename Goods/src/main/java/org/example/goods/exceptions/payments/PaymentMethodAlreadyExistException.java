package org.example.goods.exceptions.payments;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 */
public class PaymentMethodAlreadyExistException extends GlobalAppException {
    public PaymentMethodAlreadyExistException(String title) {
        super(409, "Payment method: " + title + " already exist");
    }
}
