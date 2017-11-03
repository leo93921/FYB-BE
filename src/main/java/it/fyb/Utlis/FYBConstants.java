package it.fyb.Utlis;

public class FYBConstants {

    // TODO Change for production
    public static String DOMAIN = "127.0.0.1";
    public static String BASE_URL = "http://127.0.0.1:4200";

    public static String USER_EMAIL = "e";
    public static String USER_TYPE = "t";
    public static String USER_ID = "i";
    public static String USER_NAME = "n";
    public static String USER_TOKEN = "psc";

    // If there are less than MAX_DAYS_FOR_REFUND, then the refund is
    // only the REFUND_PERCENTAGE_AFTER_MAX_DAYS of the total sale
    public static final Integer MAX_DAYS_FOR_REFUND = 7;
    public static final double REFUND_PERCENTAGE_AFTER_MAX_DAYS = 0.5;

    public static final Integer MILLISECONDS_IN_A_DAY = 86400000;
}
