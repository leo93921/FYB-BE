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

    public static final String GET_USER_GENERIC_DATA = "SELECT utente.id as u_id, Tipo, tariffa_id, Nome, Descrizione, Telefono, Paypal, indirizzo, " +
            "citta, cap, latitudine, longitudine, email, youtube_link, indirizzo_formattato, COUNT(f.id) as feedback_count, " +
            "(SUM(voto)/COUNT(f.id)) as feedback_value FROM `utente` LEFT JOIN feedback f " +
            "on f.inviato_a_id= utente.id WHERE utente.id = ?";

    public static final String SAVE_USER_GENERIC_DATA = "UPDATE `utente` SET `tariffa_id` = ?, `Nome` = ?, " +
            "`Descrizione` = ?, `Telefono` = ?, `Paypal` = ?, `indirizzo` = ?, `citta` = ?, `cap` = ?, `latitudine` = ?, " +
            "`longitudine` = ?, `indirizzo_formattato` = ?, `email` = ?, `youtube_link` = ? WHERE `id` = ?";

    public static final String CURRENT_POSITIOIN = "SELECT indirizzo_formattato as current_address FROM " +
            "utente WHERE id=?";
}