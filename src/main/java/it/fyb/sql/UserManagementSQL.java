package it.fyb.sql;

public class UserManagementSQL {

    public static final String LOGIN_USER = "SELECT * FROM `utente` WHERE `email`=? AND `password`=?";
    public static final String REGISTER_USER = "INSERT INTO `utente`(" +
            "        `Nome`," +
            "        `Telefono`," +
            "        `Tipo`," +
            "        `indirizzo`," +
            "        `citta`," +
            "        `cap`," +
            "        `latitudine`," +
            "        `longitudine`," +
            "        `indirizzo_formattato`," +
            "        `email`," +
            "        `password`)" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String GET_USER_GENERIC_DATA = "SELECT tariffa_id, Nome, Descrizione, Telefono, Paypal, indirizzo, " +
            "citta, cap, latitudine, longitudine, email, youtube_link, indirizzo_formattato FROM `utente` WHERE id = ?";

    public static final String SAVE_USER_GENERIC_DATA = "UPDATE `utente` SET `tariffa_id` = ?, `Nome` = ?, " +
            "`Descrizione` = ?, `Telefono` = ?, `Paypal` = ?, `indirizzo` = ?, `citta` = ?, `cap` = ?, `latitudine` = ?, " +
            "`longitudine` = ?, `indirizzo_formattato` = ?, `email` = ?, `youtube_link` = ? WHERE `id` = ?";
}