package it.fyb.sql;

import it.fyb.Utlis.Types;

public class EventManagerSQL {

    public static String MAKE_OFFER = "INSERT INTO `evento` (`organizzatore_id`, `gruppo_partecipante_id`, " +
            "`nome`, `descrizione`, `data`, `gruppo_messaggi`, `prezzo_concordato`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static String GET_OFFER = "SELECT * FROM `evento` WHERE `gruppo_messaggi`=?";

    public static final String UPDATE_OFFER = "UPDATE `evento` SET `nome` = ?, `descrizione` = ?, `data` = ?, " +
            "`prezzo_concordato` = ?, `is_accettata` = false WHERE `gruppo_messaggi`= ?";

    public static final String ACCEPT_OFFER = "UPDATE `evento` SET `is_accettata`=true WHERE `gruppo_messaggi`=?";
    public static final String OFFER_AS_PAID = "UPDATE `evento` SET `paid`=true WHERE `gruppo_messaggi`=?";

    public static final String GET_EVENT_INFO = "SELECT e.*, u1.Nome as placeName, " +
            "u1.indirizzo_formattato as formattedAddress FROM evento e join utente u1 on u1.id=e.organizzatore_id " +
            "where e.id=?";

    public static final String GET_EVENT_IMAGES = "SELECT url FROM media WHERE (id_utente=? or id_utente=?) " +
            "and `type` LIKE '"+ Types.IMAGE_FILES+"%' order by id_utente";

    public static final String OFFER_FROM_EVENT_ID = "select * from evento where id=?";

    public static final String OFFER_AS_REFUNDED = "update evento set refunded=true, paid=false where id=?";

    public static final String EVENT_FOR_USER_ID = "SELECT e.nome as event_name, e.`data` as event_date, " +
            "gruppo_messaggi, prezzo_concordato, is_accettata, paid, refunded, IF(u1.id=?,u2.Nome,u1.Nome) " +
            "as other_name, e.descrizione, u1.indirizzo_formattato, f.id as feedback_id, e.id as event_id, " +
            "IF(u1.id=?,u2.id,u1.id) as other_user_id FROM evento e JOIN utente u1 on u1.id=organizzatore_id " +
            "JOIN utente u2 on u2.id=gruppo_partecipante_id LEFT JOIN feedback f on f.event_id=e.id and " +
            "f.inviato_da_id=? WHERE organizzatore_id=? OR gruppo_partecipante_id=?;";
}
