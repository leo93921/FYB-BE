package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.paypal.model.Payment;
import it.fyb.sql.MediaManagementSQL;
import it.fyb.sql.PaymentSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentDAO {

    public static boolean savePaymentData(Payment payment, String messageGroup) throws Exception{
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(PaymentSQL.SAVE_PAYMENTS_DATA);
            ps.setString(1, payment.getId());
            ps.setString(2, messageGroup);
            ps.executeUpdate();
            ps.close();
        } finally {
            Utils.close(conn);
        }
        return true;
    }

}
