package org.summerserver.model.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.dao.base.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.summerserver.model.vo.StatusUpdate;

import java.util.List;

public class StatusUpdateDAOImpl extends GenericHibernateSpringDAOImpl<StatusUpdate, String> implements StatusUpdateDAO {
    public StatusUpdateDAOImpl() {
        super(StatusUpdate.class);
    }

    @Override
    public List<StatusUpdate> getAllStatusUpdates(int page) {
        int offset = (page - 1) * 10;
        int limit = 10;
        Order order = Order.desc("timestamp");

        return findByCriteria(offset, limit, order);
    }

    @Override
    public List<StatusUpdate> getAllStatusUpdatesByRelevance(int page) {
        int offset = (page - 1) * 10;
        int limit = 10;
        Order order = Order.desc("relevance");

        return findByCriteria(offset, limit, order);
    }

    @Override
    public List<StatusUpdate> getAllStatusUpdatesByUser(String user, int page) {
        int offset = (page - 1) * 10;
        int limit = 10;
        Order order = Order.desc("timestamp");
        Criterion eqCriteria = Restrictions.eq("author.name", user);

        return findByCriteria(offset, limit, order, eqCriteria);
    }
}
