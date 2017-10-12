package it.fyb.sql;

public class MediaManagementSQL {

    public static final String GET_MEDIA_FILE = "SELECT `id`, `name`, `url`, `type`, `size` FROM `media` " +
            "WHERE `id_utente`=? AND `type` LIKE ?";

    public static final String GET_FILE_BY_ID = "SELECT * FROM `media` WHERE id=?";

    public static String SAVE_MEDIA_FILE = "INSERT INTO `media`(`id_utente`,`name`,`url`,`type`,`size`)" +
            " VALUES (?,?,?,?,?);";

    public static final String DELETE_MEDIA_FILE = "DELETE FROM `media` WHERE id=?";
}
