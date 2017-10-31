package it.fyb.paypal.manager;

import it.fyb.paypal.PaypalConstants;
import it.fyb.paypal.model.*;
import jdk.nashorn.internal.parser.JSONParser;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PayPalManager implements IPayPalManager{

    private static Token activeToken;
    private static Client client = ClientBuilder.newClient();
    private static Form tokenForm = new Form();

    private static void getToken() {
        tokenForm.param("grant_type", "client_credentials");
        WebTarget target = client.target((PaypalConstants.TOKEN_ENDPOINT));
        Invocation.Builder invocationBuilder = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + PaypalConstants.PAYPAL_PASSWORD_HASH)
                .header("Accept-Language", "it_IT");
        Response response = invocationBuilder.post(Entity.form(tokenForm));
        activeToken = response.readEntity(Token.class);
        activeToken.setCreationTime(response.getDate());
    }

    private void refreshToken() {
        if (activeToken == null || activeToken.isExpired()) {
            getToken();
        }
    }

    private Invocation.Builder getBuilder(WebTarget target) {
        return target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + activeToken.getAccess_token());
    }

    @Override
    public Payment makePayment(Payment payment) {

        refreshToken();

        Invocation.Builder invocationBuilder = getBuilder(
                client.target(PaypalConstants.PAYMENT_ENDPOINT));
        Payment p = invocationBuilder.post(
                Entity.entity(payment, MediaType.APPLICATION_JSON))
                .readEntity(Payment.class);

        return p;
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) {
        refreshToken();

        WebTarget target = client.target(PaypalConstants.EXECUTE_PAYMENT_ENDPOINT
                .replace("$PAYID$", paymentId));
        Invocation.Builder invocationBuilder = getBuilder(target);

        ExecutePaymentPayload payload = new ExecutePaymentPayload();
        payload.setPayer_id(payerId);

        return invocationBuilder
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON))
                .readEntity(Payment.class);
    }

    @Override
    public Payment getPaymentInfo(String paymentId) {
        // TODO Check if this line is needed
        refreshToken();

        String endPoint = PaypalConstants.PAYMENT_ENDPOINT+"/"+paymentId;
        WebTarget target = client.target(endPoint);
        Invocation.Builder invocationBuilder = getBuilder(target);
        return invocationBuilder.get().readEntity(Payment.class);
    }

    @Override
    public Refund refundSale(String saleId, RefundSalePayload amount) {
        refreshToken();

        String endPoint = PaypalConstants.SALE_ENDPOINT + saleId + "/refund";
        WebTarget target = client.target(endPoint);
        Invocation.Builder invocationBuilder = getBuilder(target);
        return invocationBuilder
                .post(Entity.entity(amount, MediaType.APPLICATION_JSON))
                .readEntity(Refund.class);
    }
}
