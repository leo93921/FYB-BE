package it.fyb.sql;

public class EventManagerSQL {

    public static String MAKE_OFFER = "INSERT INTO `evento` (`organizzatore_id`, `gruppo_partecipante_id`, " +
            "`nome`, `descrizione`, `data`, `gruppo_messaggi`, `prezzo_concordato`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static String GET_OFFER = "SELECT * FROM `evento` WHERE `gruppo_messaggi`=?";

    public static final String UPDATE_OFFER = "UPDATE `evento` SET `nome` = ?, `descrizione` = ?, `data` = ?, " +
            "`prezzo_concordato` = ?, `is_accettata` = false WHERE `gruppo_messaggi`= ?";
}
