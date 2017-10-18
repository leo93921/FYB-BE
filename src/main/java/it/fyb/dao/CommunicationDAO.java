package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.Communication;
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
            ps.setString(4, communication.getGroup());
            ps.executeUpdate();
            ps.close();
            return groupIndex;
        } finally {
            Utils.close(conn);
        }
    }

    public static List<Communication> getCommunicationsForGroupId(String groupId) throws Exception{
        Connection conn = null;
        ResultSet rs = null;
        List<Communication> communications = new ArrayList<>();
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
                comm.setSendDate(rs.getDate("data_inviato").getTime());
                comm.setRead(rs.getBoolean("letto"));
                communications.add(comm);
            }
            ps.close();
            return communications;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }
}
