package it.fyb.dao;


import it.fyb.Utlis.Utils;
import it.fyb.model.Feedback;
import it.fyb.sql.FeedbackSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FeedbackDAO {

    public static boolean saveFeedback(Integer userTo, Integer userFrom, Feedback feedback) throws Exception{
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(FeedbackSQL.CREATE_FEEDBACK);
            ps.setInt(1, userTo);
            ps.setInt(2, userFrom);
            ps.setInt(3, feedback.getValue());
            ps.setString(4, feedback.getText());
            ps.executeUpdate();
            ps.close();
            return true;
        } finally {
            Utils.close(conn);
        }
    }
}
