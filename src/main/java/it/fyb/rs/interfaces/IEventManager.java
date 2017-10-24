package it.fyb.rs.interfaces;

import it.fyb.model.EventOffer;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/event/")
public interface IEventManager {

    @POST @Path("{groupId}") @Produces(MediaType.APPLICATION_JSON)
    boolean makeOffer(@PathParam("groupId") String groupId, EventOffer offer) throws Exception;

    @GET @Path("{groupId}") @Produces(MediaType.APPLICATION_JSON)
    EventOffer getOffer(@PathParam("groupId") String groupId) throws Exception;

    @PUT @Path("{groupId}") @Produces({MediaType.APPLICATION_JSON})
    Response acceptOffer(@PathParam("groupId") String groupId, @Context HttpHeaders httpHeaders)
            throws Exception;
}
