package it.fyb.paypal.model;

import java.util.Date;
import java.util.List;

public class Sale {

    private String id;
    private String state;
    private Amount amount;
    private String payment_mode;
    private String protection_eligibility;
    private String protection_eligibility_type;
    private TransactionFee transaction_fee;
    private List<Link> links;
    private Date create_time;
    private Date update_time;
    private String parent_payment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getProtection_eligibility() {
        return protection_eligibility;
    }

    public void setProtection_eligibility(String protection_eligibility) {
        this.protection_eligibility = protection_eligibility;
    }

    public String getProtection_eligibility_type() {
        return protection_eligibility_type;
    }

    public void setProtection_eligibility_type(String protection_eligibility_type) {
        this.protection_eligibility_type = protection_eligibility_type;
    }

    public TransactionFee getTransaction_fee() {
        return transaction_fee;
    }

    public void setTransaction_fee(TransactionFee transaction_fee) {
        this.transaction_fee = transaction_fee;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getParent_payment() {
        return parent_payment;
    }

    public void setParent_payment(String parent_payment) {
        this.parent_payment = parent_payment;
    }
}
