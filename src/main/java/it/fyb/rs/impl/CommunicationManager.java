package it.fyb.rs.impl;

import it.fyb.dao.CommunicationDAO;
import it.fyb.model.Communication;
import it.fyb.rs.interfaces.ICommunicationManager;

import java.util.List;

public class CommunicationManager implements ICommunicationManager {

    @Override
    public String saveCommunication(Communication communication) throws Exception {
        return CommunicationDAO.saveCommunication(communication);
    }

    @Override
    public List<Communication> getForGroupId(String groupId) throws Exception {
        return CommunicationDAO.getCommunicationsForGroupId(groupId);
    }

}
