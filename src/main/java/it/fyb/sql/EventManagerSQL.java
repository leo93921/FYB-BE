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
}
