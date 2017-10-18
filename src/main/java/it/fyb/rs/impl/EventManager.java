package it.fyb.rs.impl;

import it.fyb.dao.EventManagerDAO;
import it.fyb.model.EventOffer;
import it.fyb.rs.interfaces.IEventManager;

public class EventManager implements IEventManager {

    @Override
    public boolean makeOffer(String groupId, EventOffer offer) throws Exception {
        return EventManagerDAO.makeOffer(groupId, offer);
    }

    @Override
    public EventOffer getOffer(String groupId) throws Exception {
        return EventManagerDAO.getOffer(groupId);
    }
}
