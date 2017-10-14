package it.fyb.rs.impl;

import it.fyb.Utlis.Types;
import it.fyb.Utlis.Utils;
import it.fyb.dao.MediaManagementDAO;
import it.fyb.dao.UserManagementDAO;
import it.fyb.model.Media;
import it.fyb.model.RegistrationUser;
import it.fyb.model.UserGenericData;
import it.fyb.model.UserProfile;
import it.fyb.rs.interfaces.IUserManagement;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.List;

public class UserManagement implements IUserManagement {

    @Override
    public boolean registerNewUser(RegistrationUser toRegister) throws Exception {
        toRegister.setPassword(Utils.md5Encode(toRegister.getPassword()));
        return UserManagementDAO.registerUser(toRegister);
    }

    @Override
    public Response loginAction(String username, String password) throws Exception {
        String pass = Utils.md5Encode(password);
        boolean res = UserManagementDAO.checkLogin(username, pass);

        if (res) {
            // Add cookie
            NewCookie uCookie = new NewCookie("u", username);
            NewCookie rCookie = new NewCookie("role", "USER");
            return Response.status(Response.Status.OK).cookie(uCookie).cookie(rCookie).entity(true).build();
        } else {
            return Response.status(Response.Status.OK).entity(false).build();
        }
    }

    @Override
    public Response getUserGenericInfo(BigInteger id) throws Exception {
        UserGenericData user = UserManagementDAO.getUserGenericData(id);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @Override
    public boolean saveUserGenericData(BigInteger id, UserGenericData toSave) throws Exception {
        boolean res = UserManagementDAO.saveUserGenericData(id, toSave);
        return res;
    }

    @Override
    public Response getUserProfile(String userId) throws Exception {
        UserGenericData user = UserManagementDAO.getUserGenericData(BigInteger.valueOf(Long.valueOf(userId)));
        UserProfile profile = new UserProfile();
        profile.setDescription(user.getDescription());
        profile.setEmail(user.getEmail());
        profile.setId(userId);
        profile.setName(user.getName());
        profile.setPhone(user.getPhone());
        profile.setPriceBand(user.getPrice());
        profile.setYoutube(user.getYoutube());
        List<Media> images = MediaManagementDAO.getMedia(Integer.valueOf(userId), Types.IMAGE_FILES);
        profile.setImages(images);
        List<Media> music = MediaManagementDAO.getMedia(Integer.valueOf(userId), Types.AUDIO_FILES);
        profile.setMusic(music);
        return Response.status(Response.Status.OK).entity(profile).build();
    }
}
