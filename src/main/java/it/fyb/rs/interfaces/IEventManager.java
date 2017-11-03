package it.fyb.rs.interfaces;

import it.fyb.model.EventOffer;
import it.fyb.model.Media;
import it.fyb.model.PaymentInfo;

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

    @GET @Path("approve/{groupId}") @Produces(MediaType.APPLICATION_JSON)
    Response approveOffer(@PathParam("groupId") String groupId) throws Exception;

    @POST @Path("pay/{groupId}") @Produces(MediaType.APPLICATION_JSON)
    Response payOffer(@PathParam("groupId") String groupId, PaymentInfo paymentInfo) throws Exception;

    @GET @Path("/for-user/{eventId}") @Produces(MediaType.APPLICATION_JSON)
    Response getEvent(@PathParam("eventId") Integer eventId) throws Exception;

    @POST @Path("/refund/{eventId}") @Produces(MediaType.APPLICATION_JSON)
    boolean issueRefund(@PathParam("eventId") String eventId) throws Exception;
}
