package it.fyb.rs.interfaces;

import it.fyb.model.Feedback;
import it.fyb.model.Media;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/feedback/")
public interface IFeedbackManager {

    @PUT
    @Path("{userId}") @Produces(MediaType.APPLICATION_JSON)
    Response saveFeedback(@PathParam("userId") String userId, Feedback feedback,
                          @Context HttpHeaders httpHeaders) throws Exception;

    @GET
    @Path("to-be-left") @Produces(MediaType.APPLICATION_JSON)
    Response getFeedbackToBeLeft(@Context HttpHeaders httpHeaders) throws Exception;

}
