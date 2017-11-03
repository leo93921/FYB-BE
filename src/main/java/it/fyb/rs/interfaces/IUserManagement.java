package it.fyb.rs.interfaces;

import it.fyb.model.RegistrationUser;
import it.fyb.model.UserGenericData;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Path("/user")
public interface IUserManagement {

    @POST @Path("/register")
    boolean registerNewUser(RegistrationUser toRegister) throws Exception;

    @GET @Path("/login")
    Response loginAction(@QueryParam("u") String username, @QueryParam("p") String password) throws Exception;

    @GET @Path("/info/{id}")
    Response getUserGenericInfo(@PathParam("id") BigInteger id) throws Exception;

    @POST @Path("/info/{id}") @Consumes(MediaType.APPLICATION_JSON)
    boolean saveUserGenericData(@PathParam("id") BigInteger id, UserGenericData toSave) throws Exception;

    @GET @Path("/{userId}") @Produces(MediaType.APPLICATION_JSON)
    Response getUserProfile(@PathParam("userId") String userId) throws Exception;

    @GET @Path("/current-position") @Produces(MediaType.APPLICATION_JSON)
    Response getCurrentPosition(@Context HttpHeaders httpHeaders) throws Exception;
}
