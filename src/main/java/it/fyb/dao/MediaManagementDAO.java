package it.fyb.dao;

import it.fyb.Utlis.Utils;
import it.fyb.model.Media;
import it.fyb.model.UploadedMedia;
import it.fyb.sql.MediaManagementSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MediaManagementDAO {

    public static int saveMediaFile(UploadedMedia media) throws Exception {
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(MediaManagementSQL.SAVE_MEDIA_FILE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, media.getUserId());
            ps.setString(2, media.getName());
            ps.setString(3, media.getUrl());
            ps.setString(4, media.getMimeType());
            ps.setLong(5, media.getSize());
            ps.executeUpdate();
            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey != null && generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                return -1;
            }
        } finally {
            Utils.close(conn);
        }
    }

    public static List<Media> getMedia(Integer userId, String type) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        List<Media> medias = new ArrayList<Media>();
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(MediaManagementSQL.GET_MEDIA_FILE);
            String searchType = type + "%";
            ps.setLong(1, userId);
            ps.setString(2, searchType);
            rs = ps.executeQuery();
            while (rs.next()) {
                Media m = new Media();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setUrl(rs.getString("url"));
                m.setSize(rs.getLong("size"));
                m.setType(rs.getString("type"));
                medias.add(m);
            }
            return medias;
        } finally {
            Utils.close(rs);
            Utils.close(conn);
        }
    }

    public static boolean deleteMedia(Integer mediaId) throws Exception{
        Connection conn = null;
        try {
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(MediaManagementSQL.DELETE_MEDIA_FILE);
            ps.setLong(1, mediaId);
            ps.executeUpdate();
            return true;
        } finally {
            Utils.close(conn);
        }
    }

    public static Media getMediaById(Integer mediaId) throws Exception{
        Connection conn = null;
        ResultSet rs = null;
        Media m = null;
        try{
            conn = Utils.getDataConnection();
            PreparedStatement ps = conn.prepareStatement(MediaManagementSQL.GET_FILE_BY_ID);
            ps.setLong(1, mediaId);
            rs = ps.executeQuery();
            if (rs.next()) {
                m = new Media();
                m.setName(rs.getString("name"));
                m.setUrl(rs.getString("url"));
            }
            return m;
        } finally {
            Utils.close(rs);
            Utils.close(conn);
        }
    }
}
