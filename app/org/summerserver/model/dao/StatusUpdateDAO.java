package org.summerserver.model.dao;


import org.summerserver.model.dao.base.hibernate.GenericHibernateDAO;
import org.summerserver.model.vo.Comment;
import org.summerserver.model.vo.StatusUpdate;

import java.util.List;

public interface StatusUpdateDAO extends GenericHibernateDAO<StatusUpdate, Long> {
    public StatusUpdate findStatusWithComments(Long id);

    public List<StatusUpdate> getAllStatusUpdates(int page);

    public List<StatusUpdate> getAllStatusUpdatesByRelevance(int page);

    public List<StatusUpdate> getAllStatusUpdatesByUser(String user, int page);

    public Comment addComment(Long statusUpdateId, Comment comment);

    public Long countAllByUser(String username);
}
