package it.fyb.rs.interfaces;

import it.fyb.model.Media;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

@Path("/media")
public interface IMediaManagement {

    @GET @Produces(MediaType.APPLICATION_JSON) @Path("/{userId}/{type}")
    List<Media> getMediaFiles(@PathParam("userId") Integer userId, @PathParam("type") String type) throws Exception;

    @PUT @Consumes(MediaType.MULTIPART_FORM_DATA) @Produces(MediaType.APPLICATION_JSON)
    boolean saveMediaFile(@FormDataParam("media") InputStream inputStream,
                          @FormDataParam("media") FormDataContentDisposition mediaInfo,
                          @FormDataParam("title") String title,
                          @FormDataParam("userId") Integer userId,
                          @FormDataParam("mimeType") String mimeType) throws Exception;

    @DELETE @Path("/{mediaId}")
    boolean deleteMedia(@PathParam("mediaId") Integer mediaId) throws Exception;
}
