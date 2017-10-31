package it.fyb.sql;

public class FeedbackSQL {

    public static final String CREATE_FEEDBACK = "INSERT INTO `feedback`(`inviato_a_id`, " +
            "`inviato_da_id`, `voto`, `messaggio`) VALUES (?, ?, ?, ?)";
}
