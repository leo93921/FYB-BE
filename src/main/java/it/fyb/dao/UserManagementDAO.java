package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.RegistrationUser;
import it.fyb.model.UserGenericData;
import it.fyb.sql.UserManagementSQL;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManagementDAO {

    public static boolean registerUser(RegistrationUser user) throws Exception{
        Connection conn = Utils.getDataConnection();
        PreparedStatement ps = conn.prepareStatement(UserManagementSQL.REGISTER_USER);
        ps.setString(1, user.getName());
        ps.setString(2, user.getPhone());
        ps.setInt(3, user.getType());
        ps.setString(4, user.getAddress());
        ps.setString(5, user.getCity());
        ps.setString(6, user.getZipCode());
        ps.setString(7, user.getLtd());
        ps.setString(8, user.getLng());
        ps.setString(9, user.getFormattedAddress());
        ps.setString(10, user.getEmail());
        ps.setString(11, user.getPassword());
        boolean res = ps.execute();
        Utils.close(conn);
        return res;
    }

    public static boolean checkLogin(String username, String password) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(UserManagementSQL.LOGIN_USER);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            Utils.closeAll(conn, rs);
        }
    }

    public static UserGenericData getUserGenericData(BigInteger id) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        UserGenericData userData = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(UserManagementSQL.GET_USER_GENERIC_DATA);
            ps.setString(1, id.toString(16));
            rs = ps.executeQuery();
            if (rs.next()){
                userData = new UserGenericData();
                userData.setPrice(rs.getString("tariffa_id"));
                userData.setName(rs.getString("Nome"));
                userData.setDescription(rs.getString("Descrizione"));
                userData.setPhone(rs.getString("Telefono"));
                userData.setPaypal(rs.getString("Paypal"));
                userData.setAddress(rs.getString("indirizzo"));
                userData.setCity(rs.getString("citta"));
                userData.setZipCode(rs.getString("cap"));
                userData.setEmail(rs.getString("email"));
                userData.setYoutube(rs.getString("youtube_link"));
                userData.setLtd(rs.getString("latitudine"));
                userData.setLng(rs.getString("longitudine"));
                userData.setFormattedAddress(rs.getString("indirizzo_formattato"));
            }
            return userData;
        } finally {
            Utils.closeAll(conn, rs);
        }
    }

    public static boolean saveUserGenericData(BigInteger id, UserGenericData user) throws Exception {
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(UserManagementSQL.SAVE_USER_GENERIC_DATA);
            ps.setObject(1, user.getPrice());
            ps.setString(2, user.getName());
            ps.setString(3, user.getDescription());
            ps.setString(4, user.getPhone());
            ps.setObject(5, user.getPaypal());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getZipCode());
            ps.setString(9, user.getLtd());
            ps.setString(10, user.getLng());
            ps.setString(11, user.getFormattedAddress());
            ps.setString(12, user.getEmail());
            ps.setObject(13, user.getYoutube());
            ps.setInt(14, id.intValue());
            ps.execute();
            return true;
        } finally {
            Utils.close(conn);
        }
    }

}
