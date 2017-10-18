package it.fyb.rs.interfaces;

import it.fyb.model.EventOffer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/event/")
public interface IEventManager {

    @POST @Path("{groupId}") @Produces(MediaType.APPLICATION_JSON)
    boolean makeOffer(@PathParam("groupId") String groupId, EventOffer offer) throws Exception;

    @GET @Path("{groupId}") @Produces(MediaType.APPLICATION_JSON)
    EventOffer getOffer(@PathParam("groupId") String groupId) throws Exception;
}
