package it.fyb.rs.interfaces;

import it.fyb.model.Communication;
import it.fyb.model.CommunicationForList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/")
public interface ICommunicationManager{

    @PUT @Path("communication/") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
    Response saveCommunication(Communication communication, @Context HttpHeaders httpHeaders) throws Exception;

    @GET @Path("communication/{groupId}") @Produces(MediaType.APPLICATION_JSON)
    Response getForGroupId(@PathParam("groupId") String groupId, @Context HttpHeaders httpHeaders) throws Exception;

    @GET @Path("communications") @Produces(MediaType.APPLICATION_JSON)
    Response getForUser(@Context HttpHeaders httpHeaders) throws Exception;

}
