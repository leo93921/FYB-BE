package it.fyb.rs.impl;

import it.fyb.auth.AuthHelper;
import it.fyb.dao.EventManagerDAO;
import it.fyb.model.EventOffer;
import it.fyb.rs.interfaces.IEventManager;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class EventManager implements IEventManager {

    @Override
    public boolean makeOffer(String groupId, EventOffer offer) throws Exception {
        return EventManagerDAO.makeOffer(groupId, offer);
    }

    @Override
    public EventOffer getOffer(String groupId) throws Exception {
        return EventManagerDAO.getOffer(groupId);
    }

    @Override
    public Response acceptOffer(String groupId, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK)
                .entity(EventManagerDAO.acceptOffer(groupId))
                .build();
    }
}
