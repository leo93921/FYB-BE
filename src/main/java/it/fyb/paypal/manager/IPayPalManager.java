package it.fyb.paypal.manager;

import it.fyb.paypal.model.Payment;

public interface IPayPalManager {

    Payment makePayment(Payment payment);
    Payment executePayment(String paymentId, String payerId);

}
