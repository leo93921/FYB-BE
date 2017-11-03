package it.fyb.rs.interfaces;

import it.fyb.model.SearchUser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/search")
public interface ISearchManager {

    @POST @Produces(MediaType.APPLICATION_JSON)
    Response doSearch(SearchUser searchModel) throws Exception;
}
