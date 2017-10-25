package it.fyb.paypal.model;

import java.util.Date;
import java.util.List;

public class Payment {

    private String id;
    private String state;
    private Date create_time;
    private String intent;
    private Payer payer;
    private List<Transaction> transactions;
    private RedirectUrls redirect_urls;
    private List<Link> links;

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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public RedirectUrls getRedirect_urls() {
        return redirect_urls;
    }

    public void setRedirect_urls(RedirectUrls redirect_urls) {
        this.redirect_urls = redirect_urls;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
