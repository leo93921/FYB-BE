package it.fyb.rs.impl;

import it.fyb.dao.DomainDAO;
import it.fyb.model.Domain;
import it.fyb.rs.interfaces.IDomainManager;

import java.util.ArrayList;
import java.util.List;

public class DomainManager implements IDomainManager {

    private static final List<String> permittedDomains = new ArrayList<>();

    static {
        permittedDomains.add("price_band");
    }

    @Override
    public List<Domain> getDomains(String domainName) throws Exception {
        if (!permittedDomains.contains(domainName)) {
            return new ArrayList<>();
        }
        return DomainDAO.getDomain(domainName);
    }
}
