package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.Communication;
import it.fyb.model.CommunicationForList;
import it.fyb.model.CommunicationGroup;
import it.fyb.sql.CommunicationSQL;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunicationDAO {

    public static String saveCommunication(Communication communication) throws Exception {
        Connection conn = null;
        String groupIndex = UUID.randomUUID().toString();
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(CommunicationSQL.REPLY_TO_GROUP);
            ps.setInt(1, communication.getSentTo().intValue());
            ps.setInt(2, communication.getSentFrom().intValue());
            ps.setString(3, communication.getText().replace("\\n", "<br>"));
            if (communication.getGroup() == null) {
                ps.setString(4, groupIndex);
            } else {
                ps.setString(4, communication.getGroup());
            }
            ps.executeUpdate();
            ps.close();
            return communication.getGroup() == null ? groupIndex: communication.getGroup();
        } finally {
            Utils.close(conn);
        }
    }

    public static CommunicationGroup getCommunicationsForGroupId(String groupId, Integer connectedUser) throws Exception{
        Connection conn = null;
        ResultSet rs = null;
        List<Communication> communications = new ArrayList<>();
        CommunicationGroup group = new CommunicationGroup();
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(CommunicationSQL.GET_COMM_FOR_GROUP);
            ps.setString(1, groupId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Communication comm = new Communication();
                comm.setId(BigInteger.valueOf(rs.getInt("id")));
                comm.setSentTo(BigInteger.valueOf(rs.getInt("inviato_a_id")));
                comm.setSentFrom(BigInteger.valueOf(rs.getInt("inviato_da_id")));
                comm.setText(rs.getString("testo"));
                comm.setSendDate(rs.getTimestamp("data_inviato").getTime());
                comm.setRead(rs.getBoolean("letto"));
                communications.add(comm);
            }
            ps = conn.prepareStatement(CommunicationSQL.GET_COMM_USER_NAME);
            ps.setObject(1, connectedUser);
            ps.setString(2, groupId);
            rs = ps.executeQuery();
            if (rs.next()) {
                group.setName(rs.getString("_name"));
            }
            ps.close();
            group.setMessages(communications);
            return group;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }

    public static List<CommunicationForList> getCommunicationsForUserId(String userId) throws Exception{
        Connection conn = null;
        ResultSet rs = null;
        List<CommunicationForList> communications = new ArrayList<>();
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(CommunicationSQL.GET_COMM_FOR_USER);
            ps.setInt(1, Integer.valueOf(userId));
            ps.setInt(2, Integer.valueOf(userId));
            ps.setInt(3, Integer.valueOf(userId));
            ps.setInt(4, Integer.valueOf(userId));
            rs = ps.executeQuery();
            while(rs.next()) {
                CommunicationForList comm = new CommunicationForList();
                comm.setGroupId(rs.getString("gruppo"));
                comm.setLastTime(rs.getTimestamp("last_contact").getTime());
                comm.setName(rs.getString("send_user"));
                comm.setUnread(rs.getBoolean("unread"));
                comm.setText(rs.getString("testo"));
                communications.add(comm);
            }
            ps.close();
            return communications;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }

    public static void setCommunicationsAsRead(List<Communication> communications, Integer connectedUser) throws Exception {
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(CommunicationSQL.SET_COMM_AS_READ);
            BigInteger userId = BigInteger.valueOf(connectedUser);
            for (Communication communication : communications) {
                if (communication.getSentTo().equals(userId)) {
                    ps.setObject(1, communication.getId());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
            ps.close();
        } finally {
            Utils.close(conn);
        }
    }
}
