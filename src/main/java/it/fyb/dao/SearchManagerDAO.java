package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.Position;
import it.fyb.model.SearchResultItem;
import it.fyb.model.SearchUser;
import it.fyb.sql.SearchSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchManagerDAO {


    public static List<SearchResultItem> search(SearchUser searchModel) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = Utils.getDataConnection();
            String sql = createSearchSql(searchModel);
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<SearchResultItem> results = new ArrayList<>();
            if (searchModel.getPosition() != null) {
                while (rs.next()) {
                    SearchResultItem item = new SearchResultItem();
                    item.setDescription(rs.getString("Descrizione"));
                    item.setType(rs.getInt("Tipo"));
                    item.setDistance(rs.getFloat("distance"));
                    results.add(item);
                }
            } else {
                while (rs.next()) {
                    SearchResultItem item = new SearchResultItem();
                    item.setDescription(rs.getString("Descrizione"));
                    item.setType(rs.getInt("Tipo"));
                    results.add(item);
                }
            }
            return results;
        } finally {
            Utils.closeAll(conn, rs);
        }


    }

    private static String createSearchSql(SearchUser searchModel) {
        // Building SQL
        String havingClause = "";
        Float maxDistance = searchModel.getMaxDistance();
        if (maxDistance != null && maxDistance != 0) {
            havingClause = "HAVING distance <= " + maxDistance;
        }
        Integer type = searchModel.getType();
        String keywords = searchModel.getKeywords();
        List<String> whereClauses = new ArrayList<>();
        if ((type != null && type != 0)){
            whereClauses.add("Tipo = " + type);
        }
        if (keywords != null && !keywords.trim().equals("")) {
            whereClauses.add("descrizione like '%" + keywords + "%'");
        }
        String whereClause = "";
        if (whereClauses.size() != 0) {
            whereClause = " WHERE " + whereClauses.get(0);
            for (int i = 1; i < whereClauses.size(); i++) {
                whereClause = whereClause + " AND " + whereClauses.get(i);
            }
        }
        String distanceClause = "";
        Position position = searchModel.getPosition();
        if (position != null) {
            distanceClause = ", st_distance_sphere(POINT(" + position.getLng() + ", "
                    + position.getLtd() +"), utente.geopoint) as distance ";
        } else {
            havingClause = "";
        }
        return SearchSQL.SEARCH_USER
                .replace("%DISTANCE_CLAUSE%", distanceClause)
                .replace("%WHERE_CLAUSE%", whereClause)
                .replace("%HAVING_CLAUSE%", havingClause);
    }


}
