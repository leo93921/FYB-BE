package it.fyb.sql;

public class FeedbackSQL {

    public static final String CREATE_FEEDBACK = "INSERT INTO `feedback`(`inviato_a_id`, " +
            "`inviato_da_id`, `voto`, `messaggio`, `event_id`) VALUES (?, ?, ?, ?, ?)";

    public static final String GET_FEED_TO_LEAVE = "SELECT e.id, e.nome, e.`data`, u.Nome as userName, " +
            "u.id as userId FROM evento e LEFT JOIN feedback f ON f.event_id=e.id and f.inviato_da_id=? " +
            "JOIN utente u ON u.id = if(organizzatore_id=?, gruppo_partecipante_id, " +
            "organizzatore_id) WHERE (e.gruppo_partecipante_id=? OR e.organizzatore_id=?) " +
            "AND (f.id is null)";
}
