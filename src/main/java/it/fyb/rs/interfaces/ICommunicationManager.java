package it.fyb.rs.interfaces;

import it.fyb.model.Communication;
import it.fyb.model.Media;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/communication/")
public interface ICommunicationManager{

    @PUT @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
    String saveCommunication(Communication communication) throws Exception;

    @GET @Path("{groupId}") @Produces(MediaType.APPLICATION_JSON)
    List<Communication> getForGroupId(@PathParam("groupId") String groupId) throws Exception;

}
