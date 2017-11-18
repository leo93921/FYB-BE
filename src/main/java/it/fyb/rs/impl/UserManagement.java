package it.fyb.rs.impl;

import it.fyb.Utlis.FYBConstants;
import it.fyb.Utlis.Types;
import it.fyb.Utlis.Utils;
import it.fyb.auth.AuthHelper;
import it.fyb.auth.AuthManager;
import it.fyb.auth.AuthToken;
import it.fyb.dao.FeedbackDAO;
import it.fyb.dao.MediaManagementDAO;
import it.fyb.dao.UserManagementDAO;
import it.fyb.model.*;
import it.fyb.rs.interfaces.IUserManagement;

import javax.ws.rs.core.HttpHeaders;
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
    public Response loginAction(String email, String password) throws Exception {
        String pass = Utils.md5Encode(password);
        RegistrationUser res = UserManagementDAO.checkLogin(email, pass);
        if (res != null) {
            String role;
            if (res.getType() == Types.GROUP_TYPE) {
                role = Types.GROUP_TYPE_TEXT;
            } else {
                role = Types.PLACE_TYPE_TEXT;
            }

            // Get token when connect
            AuthToken token = AuthManager.getToken(res);

            // Add cookie
            NewCookie userCookie = new NewCookie(FYBConstants.USER_EMAIL, email, "/", FYBConstants.DOMAIN, "", NewCookie.DEFAULT_MAX_AGE, false);
            NewCookie typeCookie = new NewCookie(FYBConstants.USER_TYPE, role, "/", FYBConstants.DOMAIN, "", NewCookie.DEFAULT_MAX_AGE, false);
            NewCookie idCookie = new NewCookie(FYBConstants.USER_ID, String.valueOf(res.getId()), "/", FYBConstants.DOMAIN, "", NewCookie.DEFAULT_MAX_AGE, false);
            NewCookie nameCookie = new NewCookie(FYBConstants.USER_NAME, res.getName(), "/", FYBConstants.DOMAIN, "", NewCookie.DEFAULT_MAX_AGE, false);
            NewCookie passCookie = new NewCookie(FYBConstants.USER_TOKEN, token.getToken(), "/", FYBConstants.DOMAIN, "", NewCookie.DEFAULT_MAX_AGE, false);
            return Response.status(Response.Status.OK)
                    .cookie(userCookie)
                    .cookie(typeCookie)
                    .cookie(idCookie)
                    .cookie(nameCookie)
                    .cookie(passCookie)
                    .entity(true).build();
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
        profile.setAddress(user.getFormattedAddress());
        profile.setDescription(user.getDescription());
        profile.setType(user.getType());
        profile.setEmail(user.getEmail());
        profile.setId(userId);
        profile.setName(user.getName());
        profile.setPhone(user.getPhone());
        profile.setPriceBand(user.getPrice());
        profile.setYoutube(user.getYoutube());
        profile.setFeedbackCount(user.getFeedbackCount());
        profile.setFeedbackValue(user.getFeedbackValue());
        profile.setFeedbackContainer(FeedbackDAO.getFeedback(userId));
        for (FeedbackCount c : profile.getFeedbackContainer().getCounts()) {
            c.setPercentage(Float.valueOf(c.getCount()) / user.getFeedbackCount() * 10);
        }

        List<Media> images = MediaManagementDAO.getMedia(Integer.valueOf(userId), Types.IMAGE_FILES);
        profile.setImages(images);
        List<Media> music = MediaManagementDAO.getMedia(Integer.valueOf(userId), Types.AUDIO_FILES);
        profile.setMusic(music);
        return Response.status(Response.Status.OK).entity(profile).build();
    }

    @Override
    public Response getCurrentPosition(HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Integer userId = Integer.valueOf(httpHeaders.getCookies()
                .get(FYBConstants.USER_ID).getValue());
        return Response.status(Response.Status.OK)
                .entity(UserManagementDAO.getCurrentPosition(userId))
                .build();
    }
}
