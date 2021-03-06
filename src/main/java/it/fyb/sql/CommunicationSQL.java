package it.fyb.sql;

public class CommunicationSQL {

    public static final String SAVE_NEW_COMMUNICATION = "INSERT INTO `comunication` (`inviato_a_id`, `inviato_da_id`, " +
            "`testo`, `data`, `data_inviato`, `letto`, `gruppo`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_COMM_FOR_GROUP = "SELECT * FROM `comunication` WHERE `gruppo`=? ORDER BY `data_inviato`";

    public static final String REPLY_TO_GROUP = "INSERT INTO `comunication` (`inviato_a_id`, `inviato_da_id`, " +
            "`testo`, `gruppo`) VALUES (?, ?, ?, ?)";

    public static final String GET_COMM_FOR_USER = "select IF(inviato_a_id=?,u2.Nome,u1.Nome) AS send_user, " +
            "c.gruppo, c.testo, j.last_contact, r.unread from comunication c join (select max(data_inviato) as " +
            "last_contact, gruppo from comunication group by gruppo) j on j.gruppo = c.gruppo " +
            "join (select IF(SUM(IF(letto,0,1))>0,true,false) as unread, gruppo from comunication where inviato_a_id=? group by gruppo) r on r.gruppo = c.gruppo " +
            "join utente u1 on inviato_a_id=u1.id join utente u2 on inviato_da_id=u2.id " +
            "where j.gruppo = c.gruppo and j.last_contact=c.data_inviato and " +
            "(c.inviato_a_id=? OR c.inviato_da_id=?)";

    public static final String SET_COMM_AS_READ = "UPDATE `comunication` SET `letto`=true WHERE id=?";

    public static final String GET_COMM_USER_NAME = "SELECT IF(c.inviato_a_id=?, u2.Nome, u1.Nome) as _name  FROM `comunication` c " +
            "JOIN utente u1 ON c.inviato_a_id=u1.id JOIN utente u2 ON c.inviato_da_id=u2.id " +
            "WHERE `gruppo`=? LIMIT 1";
}
