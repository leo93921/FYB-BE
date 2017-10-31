package it.fyb.rs.impl;

import it.fyb.Utlis.FYBConstants;
import it.fyb.auth.AuthHelper;
import it.fyb.dao.FeedbackDAO;
import it.fyb.model.Feedback;
import it.fyb.rs.interfaces.IFeedbackManager;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class FeedbackManager implements IFeedbackManager {

    @Override
    public Response saveFeedback(String userId, Feedback feedback, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Integer connectedUser = Integer.valueOf(httpHeaders.getCookies().get(FYBConstants.USER_ID).getValue());
        return Response
                .status(Response.Status.OK)
                .entity(FeedbackDAO.saveFeedback(Integer.valueOf(userId), connectedUser, feedback))
                .build();
    }

    @Override
    public Response getFeedbackToBeLeft(HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String connectedUser = httpHeaders.getCookies()
                .get(FYBConstants.USER_ID).getValue();

        return Response
                .status(Response.Status.OK)
                .entity(FeedbackDAO.getFeedbackToBeLeft(connectedUser))
                .build();
    }
}
