package it.fyb.paypal.model;

public class RefundSalePayload {

    private RefundAmount amount;

    public RefundSalePayload(double amount, String currency) {
        this.amount = new RefundAmount();
        this.amount.setCurrency(currency);
        this.amount.setTotal(amount);
    }

    public RefundAmount getAmount() {
        return amount;
    }

    public void setAmount(RefundAmount amount) {
        this.amount = amount;
    }
}
