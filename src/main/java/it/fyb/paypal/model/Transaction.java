package it.fyb.paypal.model;

import java.util.List;

public class Transaction {

    private Amount amount;
    private PaymentOption payment_options;
    private ItemList item_list;
    private List<RelatedResource> related_resources;

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public PaymentOption getPayment_options() {
        return payment_options;
    }

    public void setPayment_options(PaymentOption payment_options) {
        this.payment_options = payment_options;
    }

    public ItemList getItem_list() {
        return item_list;
    }

    public void setItem_list(ItemList item_list) {
        this.item_list = item_list;
    }

    public List<RelatedResource> getRelated_resources() {
        return related_resources;
    }

    public void setRelated_resources(List<RelatedResource> related_resources) {
        this.related_resources = related_resources;
    }
}
