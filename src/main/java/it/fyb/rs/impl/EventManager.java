package it.fyb.rs.impl;

import it.fyb.Utlis.FYBConstants;
import it.fyb.auth.AuthHelper;
import it.fyb.dao.EventManagerDAO;
import it.fyb.dao.PaymentDAO;
import it.fyb.model.EventOffer;
import it.fyb.model.EventOfferWithPay;
import it.fyb.model.EventWithAction;
import it.fyb.model.PaymentInfo;
import it.fyb.paypal.PaypalConstants;
import it.fyb.paypal.manager.PayPalManager;
import it.fyb.paypal.model.*;
import it.fyb.rs.interfaces.IEventManager;

import javax.swing.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class EventManager implements IEventManager {

    @Override
    public boolean makeOffer(String groupId, EventOffer offer) throws Exception {
        // TODO accepted offers cannot be changed
        // TODO in every service, add authentication. Where needed
        return EventManagerDAO.makeOffer(groupId, offer);
    }

    @Override
    public EventOffer getOffer(String groupId) throws Exception {
        return EventManagerDAO.getOffer(groupId);
    }

    @Override
    public Response acceptOffer(String groupId, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK)
                .entity(EventManagerDAO.acceptOffer(groupId))
                .build();
    }

    @Override
    public Response approveOffer(String groupId, Boolean fromActions) throws Exception {
        EventOffer offer = EventManagerDAO.getOffer(groupId);
        // TODO Handle exceptions in html!
        if (!offer.isAccepted()){
            throw new Exception("L'offerta deve essere prima accetta");
        }
        if (offer.isPaid()) {
            throw new Exception("L'offerta è già stata accettata");
        }

        Payment p = this.createPaymentObject(offer, groupId, fromActions);

        PayPalManager paypalManager = new PayPalManager();
        // Response object from PayPal
        Payment payment = paypalManager.makePayment(p);

        // Save payment id
        PaymentDAO.savePaymentData(payment, groupId);

        // Get redirect url for approving payment
        String redirectUrl = "";
        for (Link link : payment.getLinks()) {
            if (link.getRel().equals(Link.APPROVAL_REL)){
                redirectUrl = link.getHref();
                break;
            }
        }

        // Return response
        return Response.status(Response.Status.OK).entity(redirectUrl).build();
    }

    @Override
    public Response payOffer(String groupId, PaymentInfo paymentInfo) throws Exception {
        PayPalManager payPalManager = new PayPalManager();
        payPalManager.executePayment(paymentInfo.getPaymentId(), paymentInfo.getPayerID());

        Payment payment = payPalManager.getPaymentInfo(paymentInfo.getPaymentId());
        Boolean isCompleted = payment.getTransactions().get(0)
                .getRelated_resources().get(0)
                .getSale().getState().equals(Sale.SALE_COMPLETED_STATUS);
        if (isCompleted)  {
            EventManagerDAO.setEventAsPaid(groupId);
        }
        return Response.status(Response.Status.OK).entity(true).build();
    }

    @Override
    public Response getEvent(Integer eventId) throws Exception {
        return Response.status(Response.Status.OK)
                .entity(EventManagerDAO.getEventInfo(eventId))
                .build();
    }

    @Override
    public boolean issueRefund(String eventId) throws Exception {
        // TODO handle not paid
        EventOfferWithPay eventOffer = EventManagerDAO.getOfferFromEventId(eventId);

        PayPalManager payPalManager = new PayPalManager();
        Payment payment = payPalManager.getPaymentInfo(eventOffer.getPaymentId());
        Sale sale = payment.getTransactions().get(0)
                .getRelated_resources().get(0)
                .getSale();
        // TODO check completed state for sale
        // Calculate value of refund
        Boolean isFullRefund = sale.getCreate_time().getTime() <
                (eventOffer.getEventDate().getTime() - FYBConstants.MILLISECONDS_IN_A_DAY*FYBConstants.MAX_DAYS_FOR_REFUND);
        Refund refund;
        if (isFullRefund) {
            refund = payPalManager.refundSale(sale.getId(), null);
        } else {
            double total =
                    (sale.getAmount().getTotal() - sale.getTransaction_fee().getValue()) * FYBConstants.REFUND_PERCENTAGE_AFTER_MAX_DAYS;
            String currency = sale.getAmount().getCurrency();
            RefundSalePayload amount = new RefundSalePayload(total, currency);
            refund = payPalManager.refundSale(sale.getId(), amount);
        }
        if (refund.getState().equals("failed")) {
            return false;
        } else {
            EventManagerDAO.setEventAsRefunded(eventId);
            return true;
        }
    }

    @Override
    public Response getEventForConnectedUser(HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Integer userId = Integer.valueOf(httpHeaders.getCookies()
                .get(FYBConstants.USER_ID)
                .getValue());
        List<EventWithAction> events = EventManagerDAO.getEventWithAction(userId);
        return Response
                .status(Response.Status.OK)
                .entity(events)
                .build();
    }

    private Payment createPaymentObject(EventOffer offer, String messageGroup, Boolean fromAction) {
        String payDescription = "Evento: "+ offer.getName()+"; ID: " + messageGroup;

        Payment p = new Payment();
        // Intent
        p.setIntent(PaypalConstants.INTENT_SALE);

        // Payer
        Payer payer = new Payer();
        payer.setPayment_method(PaypalConstants.PAYMENT_METHOD);
        p.setPayer(payer);

        // Redirect url
        RedirectUrls urls = new RedirectUrls();
        if (fromAction) {
            urls.setCancel_url(FYBConstants.BASE_URL + "/actions");

        }else {
            urls.setCancel_url(FYBConstants.BASE_URL + "/messages/"+messageGroup);
        }
        urls.setReturn_url(FYBConstants.BASE_URL + "/elaborate/"+messageGroup+"?fromAction="+fromAction);
        p.setRedirect_urls(urls);

        //Transaction
        Transaction transaction = new Transaction();
        // Amount
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        //Detail
        Details details = new Details();
        details.setSubtotal(offer.getPrice());
        details.setShipping(0);
        details.setTax(0);
        amount.setDetails(null);
        amount.setTotal(offer.getPrice());
        transaction.setAmount(amount);

        //Transaction ITEM
        Item item = new Item();
        item.setCurrency("EUR");
        item.setDescription(payDescription);
        item.setName("Evento: " + offer.getName());
        item.setPrice(offer.getPrice());
        item.setQuantity(1);
        ItemList itemList = new ItemList();
        itemList.getItems().add(item);
        transaction.setItem_list(itemList);

        // Payment options
        PaymentOption option = new PaymentOption();
        option.setAllowed_payment_method(PaypalConstants.PAYMENT_IMMEDIATE_PAYMENT);
        transaction.setPayment_options(option);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        p.setTransactions(transactions);
        return p;
    }
}
