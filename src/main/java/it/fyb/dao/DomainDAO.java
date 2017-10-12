package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.Domain;
import it.fyb.sql.DomainManagerSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DomainDAO {

    public static List<Domain> getDomain(String domainName) throws Exception{
        Connection conn = null;
        ResultSet rs = null;
        List<Domain> domains;
        try {
            conn = Utils.getDataConnection();
            String sql = DomainManagerSQL.GET_DOMAIN.replace("_TABLE_NAME_", domainName);
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            domains = new ArrayList<>();
            while (rs.next()) {
                Domain d = new Domain();
                d.setCode(rs.getString("id"));
                d.setText(rs.getString("minimo")+"-"+rs.getString("massimo"));
                domains.add(d);
            }
            return domains;
        } finally {
            Utils.close(rs);
            Utils.close(conn);
        }
    }

}
