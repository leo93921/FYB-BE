package it.fyb.rs.impl;

import it.fyb.auth.AuthHelper;
import it.fyb.dao.CommunicationDAO;
import it.fyb.model.Communication;
import it.fyb.model.CommunicationForList;
import it.fyb.rs.interfaces.ICommunicationManager;

import javax.ws.rs.core.HttpHeaders;
import java.util.List;

public class CommunicationManager implements ICommunicationManager {

    @Override
    public String saveCommunication(Communication communication) throws Exception {
        return CommunicationDAO.saveCommunication(communication);
    }

    @Override
    public List<Communication> getForGroupId(String groupId, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return null;
        }
        return CommunicationDAO.getCommunicationsForGroupId(groupId);
    }

    @Override
    public List<CommunicationForList> getForUser(String userId, HttpHeaders httpHeaders) throws Exception {
        if (!AuthHelper.checkAuthentication(httpHeaders)) {
            return null;
        }
        return CommunicationDAO.getCommunicationsForUserId(userId);
    }

}
