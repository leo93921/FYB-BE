package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.EventOffer;
import it.fyb.rs.impl.EventManager;
import it.fyb.sql.EventManagerSQL;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventManagerDAO {

    public static boolean makeOffer(String groupId, EventOffer offer) throws Exception {
        // TODO offer can be made only by PLACES.
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement psGet = conn.prepareStatement(EventManagerSQL.GET_OFFER);
            psGet.setString(1, groupId);
            rs = psGet.executeQuery();
            PreparedStatement ps;
            if (rs.next()) {
                if (rs.getBoolean("is_accettata"))
                    return false;
                ps = conn.prepareStatement(EventManagerSQL.UPDATE_OFFER);
                ps.setString(1, offer.getName());
                ps.setString(2, offer.getDescription());
                ps.setDate(3, new Date(offer.getDate()));
                ps.setFloat(4, offer.getPrice());
                ps.setString(5, groupId);
            } else {
                ps = conn.prepareStatement(EventManagerSQL.MAKE_OFFER);
                ps.setInt(1, offer.getPlaceId().intValue());
                ps.setInt(2, offer.getGroupId().intValue());
                ps.setString(3, offer.getName());
                ps.setString(4, offer.getDescription());
                ps.setDate(5, new Date(offer.getDate()));
                ps.setString(6, groupId);
                ps.setFloat(7, offer.getPrice());
            }
            ps.executeUpdate();
            ps.close();
            psGet.close();
            return true;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }

    public static EventOffer getOffer(String groupId) throws Exception{
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement psGet = conn.prepareStatement(EventManagerSQL.GET_OFFER);
            psGet.setString(1, groupId);
            rs = psGet.executeQuery();
            EventOffer offer = new EventOffer();
            if (rs.next()) {
                offer.setId(rs.getInt("id"));
                offer.setPlaceId(rs.getBigDecimal("organizzatore_id").toBigInteger());
                offer.setGroupId(rs.getBigDecimal("gruppo_partecipante_id").toBigInteger());
                offer.setName(rs.getString("nome"));
                offer.setDescription(rs.getString("descrizione"));
                offer.setDate(rs.getDate("data").getTime());
                offer.setAccepted(rs.getBoolean("is_accettata"));
                offer.setPaid(rs.getBoolean("paid"));
                offer.setPrice(rs.getFloat("prezzo_concordato"));
            } else {
                return null;
            }
            psGet.close();
            return offer;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }


    public static Object acceptOffer(String groupId) throws Exception{
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(EventManagerSQL.ACCEPT_OFFER);
            ps.setString(1, groupId);
            ps.executeUpdate();
            ps.close();
            return true;
        } finally {
            Utils.close(conn);
        }
    }

    public static void setEventAsPaid(String groupId) throws Exception{
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(EventManagerSQL.OFFER_AS_PAID);
            ps.setString(1, groupId);
            ps.executeUpdate();
            ps.close();
        } finally {
            Utils.close(conn);
        }
    }
}
