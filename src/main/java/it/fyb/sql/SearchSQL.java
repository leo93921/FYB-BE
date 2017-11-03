package it.fyb.sql;

public class SearchSQL {

    public static final String SEARCH_USER = "select * %DISTANCE_CLAUSE%" +
            "from utente %WHERE_CLAUSE% %HAVING_CLAUSE%";

}