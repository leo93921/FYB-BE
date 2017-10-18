package it.fyb.sql;

public class CommunicationSQL {

    public static final String SAVE_NEW_COMMUNICATION = "INSERT INTO `comunication` (`inviato_a_id`, `inviato_da_id`, " +
            "`testo`, `data`, `data_inviato`, `letto`, `gruppo`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_COMM_FOR_GROUP = "SELECT * FROM `comunication` WHERE `gruppo`=? ORDER BY `data_inviato`";

    public static final String REPLY_TO_GROUP = "INSERT INTO `comunication` (`inviato_a_id`, `inviato_da_id`, " +
            "`testo`, `gruppo`) VALUES (?, ?, ?, ?)";
}
