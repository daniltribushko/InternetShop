package org.example.goods.exceptions.payments;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 */
public class PaymentMethodByIdNotFoundException extends GlobalAppException {
    public PaymentMethodByIdNotFoundException(Long id) {
        super(404, "Payment method with id " + id + " not found");
    }
}
