package it.fyb.dao;


import it.fyb.Utlis.Utils;
import it.fyb.model.Feedback;
import it.fyb.model.FeedbackContainer;
import it.fyb.model.FeedbackCount;
import it.fyb.model.FeedbackRequest;
import it.fyb.sql.FeedbackSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FeedbackDAO {

    public static boolean saveFeedback(Integer userTo, Integer userFrom, Feedback feedback) throws Exception {
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(FeedbackSQL.CREATE_FEEDBACK);
            ps.setInt(1, userTo);
            ps.setInt(2, userFrom);
            ps.setInt(3, feedback.getValue());
            ps.setString(4, feedback.getText());
            ps.setInt(5, feedback.getEventId());
            ps.executeUpdate();
            ps.close();
            return true;
        } finally {
            Utils.close(conn);
        }
    }

    public static List<FeedbackRequest> getFeedbackToBeLeft(String connectedUser)
            throws Exception {
        List<FeedbackRequest> requests = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(FeedbackSQL.GET_FEED_TO_LEAVE);
            Integer userId = Integer.valueOf(connectedUser);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.setInt(4, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                FeedbackRequest req = new FeedbackRequest();
                req.setEventDate(rs.getTimestamp("data").getTime());
                req.setEventId(rs.getString("id"));
                req.setEventName(rs.getString("nome"));
                req.setUserName(rs.getString("userName"));
                req.setUserId(rs.getString("userId"));
                requests.add(req);
            }
            return requests;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }

    public static FeedbackContainer getFeedback(String connectedUser)
            throws Exception {
        Connection connection = null;
        ResultSet rs = null;
        try {
            FeedbackContainer container = new FeedbackContainer();
            connection = Utils.getDataConnection();
            // Counts
            PreparedStatement ps = connection.prepareStatement(FeedbackSQL.GET_FEEDBACK_COUNT);
            ps.setObject(1, connectedUser);
            rs = ps.executeQuery();
            while (rs.next()) {
                FeedbackCount c = new FeedbackCount();
                c.setCount(rs.getInt("count"));
                c.setVote(rs.getInt("voto"));
                container.getCounts().add(c);
            }
            // Last 5 feedback
            ps = connection.prepareStatement(FeedbackSQL.GET_LAST_FEEDBACK);
            ps.setObject(1, connectedUser);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setTitle(rs.getString("title"));
                f.setText(rs.getString("messaggio"));
                f.setValue(rs.getInt("voto"));
                f.setWhen(rs.getTime("creation_date").getTime());
                container.getFeedback().add(f);
            }
            ps.close();
            return container;
        } finally {
            Utils.closeAll(connection, rs);
        }
    }
}
