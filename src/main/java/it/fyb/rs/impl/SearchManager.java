package it.fyb.rs.impl;

import it.fyb.dao.SearchManagerDAO;
import it.fyb.model.SearchResultItem;
import it.fyb.model.SearchUser;
import it.fyb.rs.interfaces.ISearchManager;

import javax.ws.rs.core.Response;
import java.util.List;

public class SearchManager implements ISearchManager{

    @Override
    public Response doSearch(SearchUser searchModel) throws Exception {

        List<SearchResultItem> results = SearchManagerDAO.search(searchModel);
        return Response.status(Response.Status.OK).entity(results).build();
    }

}
