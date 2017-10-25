package it.fyb.paypal.manager;

import it.fyb.paypal.PaypalConstants;
import it.fyb.paypal.model.Payment;
import it.fyb.paypal.model.Token;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

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


    @Override
    public Payment makePayment(Payment payment) {

        if (activeToken == null || activeToken.isExpired()) {
            getToken();
        }

        WebTarget target = client.target(PaypalConstants.PAYMENT_ENDPOINT);
        Invocation.Builder invocationBuilder = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + activeToken.getAccess_token());
        Payment p = invocationBuilder.post(
                Entity.entity(payment, MediaType.APPLICATION_JSON))
                .readEntity(Payment.class);

        return p;
    }
}
