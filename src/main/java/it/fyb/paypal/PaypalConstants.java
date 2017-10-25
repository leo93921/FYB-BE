package it.fyb.paypal;

public class PaypalConstants {

    public static String PAYPAL_PASSWORD_HASH = "QVNFaU9aSThBWTJDLXdUY3dWLXNqa0dzSVNuZkJUeWYtTldTZFl" +
            "LMC1IbmF3eEVDVmJGMjFYdmgxQWxiUkgxbDdYZzVBMFhNS0Q1MGs5SXA6RU9uSTlXVjhrTnM4TEZZdFdJVkNGdk" +
            "NnQTVWSUJvcUNpRUlLM2dEUUdEMWhtV3RoTTh0QWRUQUJ5RWROSTJSQUpkSVZ6YjluaHdLTnVEb2g=";

    public static String PAYPAL_BUSINESS = "leonardo.capoccia-facilitator@hotmail.it";

    public static String TOKEN_ENDPOINT = "https://api.sandbox.paypal.com/v1/oauth2/token";
    public static String PAYMENT_ENDPOINT = "https://api.sandbox.paypal.com/v1/payments/payment";

    public static String INTENT_SALE = "sale";
    public static String PAYMENT_METHOD = "paypal";
    public static final String PAYMENT_IMMEDIATE_PAYMENT = "IMMEDIATE_PAY";

}
