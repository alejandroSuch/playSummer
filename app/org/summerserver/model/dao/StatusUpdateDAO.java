package org.summerserver.model.dao;


import org.summerserver.model.dao.base.hibernate.GenericHibernateDAO;
import org.summerserver.model.vo.StatusUpdate;

import java.util.List;

public interface StatusUpdateDAO extends GenericHibernateDAO<StatusUpdate, String> {
    public List<StatusUpdate> getAllStatusUpdates(int page);
    public List<StatusUpdate> getAllStatusUpdatesByRelevance(int page);
    public List<StatusUpdate> getAllStatusUpdatesByUser(String user, int page);
}
