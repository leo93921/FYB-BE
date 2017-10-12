package it.fyb.rs.interfaces;

import it.fyb.model.Domain;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/domain/")
public interface IDomainManager {

    @GET @Path("{domainName}") @Produces(MediaType.APPLICATION_JSON)
    List<Domain> getDomains(@PathParam("domainName") String domainName) throws Exception;
}
