package it.fyb.paypal.manager;

import it.fyb.paypal.model.Payment;
import it.fyb.paypal.model.Refund;
import it.fyb.paypal.model.RefundSalePayload;

public interface IPayPalManager {

    Payment makePayment(Payment payment);
    Payment executePayment(String paymentId, String payerId);
    Payment getPaymentInfo(String paymentId);
    Refund refundSale(String saleId, RefundSalePayload amount);
}
