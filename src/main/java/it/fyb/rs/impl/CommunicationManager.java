package it.fyb.rs.impl;

import it.fyb.Utlis.FYBConstants;
import it.fyb.auth.AuthHelper;
import it.fyb.dao.CommunicationDAO;
import it.fyb.model.Communication;
import it.fyb.model.CommunicationForList;
import it.fyb.rs.interfaces.ICommunicationManager;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

public class CommunicationManager implements ICommunicationManager {

    @Override
    public Response saveCommunication(Communication communication, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK)
                .entity(CommunicationDAO.saveCommunication(communication))
                .build();
    }

    @Override
    public Response getForGroupId(String groupId, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Integer connectedUser = Integer.valueOf(httpHeaders.getCookies().get(FYBConstants.USER_ID).getValue());
        List<Communication> communications = CommunicationDAO.getCommunicationsForGroupId(groupId);
        CommunicationDAO.setCommunicationsAsRead(communications, connectedUser);
        return Response
                .status(Response.Status.OK)
                .entity(communications)
                .build();
    }

    @Override
    public Response getForUser(HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK)
                .entity(CommunicationDAO.getCommunicationsForUserId(
                        httpHeaders.getCookies().get(FYBConstants.USER_ID).getValue()))
                .build();
    }

}
