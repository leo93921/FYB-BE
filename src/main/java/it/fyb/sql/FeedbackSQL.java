package it.fyb.sql;

public class FeedbackSQL {

    public static final String CREATE_FEEDBACK = "INSERT INTO `feedback`(`inviato_a_id`, " +
            "`inviato_da_id`, `voto`, `messaggio`, `event_id`) VALUES (?, ?, ?, ?, ?)";

    public static final String GET_FEED_TO_LEAVE = "SELECT e.id, e.nome, e.`data`, u.Nome as userName, " +
            "u.id as userId FROM evento e LEFT JOIN feedback f ON f.event_id=e.id and f.inviato_da_id=? " +
            "JOIN utente u ON u.id = if(organizzatore_id=?, gruppo_partecipante_id, " +
            "organizzatore_id) WHERE (e.gruppo_partecipante_id=? OR e.organizzatore_id=?) " +
            "AND (f.id is null)";

    public static final String GET_FEEDBACK_COUNT = "SELECT  vote as voto, count(id) as count FROM " +
            "( SELECT 1 as vote UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) s " +
            "left join feedback f on voto=s.vote and f.inviato_a_id=? group by vote";

    public static final String GET_LAST_FEEDBACK = "SELECT * FROM feedback WHERE inviato_a_id = ?" +
            "order by creation_date desc limit 5";
}
