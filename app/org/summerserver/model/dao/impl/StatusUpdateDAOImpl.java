package org.summerserver.model.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.dao.base.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.summerserver.model.vo.StatusUpdate;

import java.util.List;

public class StatusUpdateDAOImpl extends GenericHibernateSpringDAOImpl<StatusUpdate, Long> implements StatusUpdateDAO {
    public StatusUpdateDAOImpl() {
        super(StatusUpdate.class);
    }

    @Override
    public StatusUpdate findStatusWithComments(Long id) {

        StatusUpdate statusUpdate = (StatusUpdate)getSession().get(StatusUpdate.class, id);
        //StatusUpdate statusUpdate = getById(id, false);
        getHibernateTemplate().merge(statusUpdate);
        System.out.println("comments are: " + statusUpdate.getComments());
        return statusUpdate;
    }

    @Override
    public List<StatusUpdate> getAllStatusUpdates(int page) {
        int offset = (page - 1) * 10;
        int limit = 10;
        Order order = Order.desc("date");

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
        Order order = Order.desc("date");
        Criterion eqCriteria = Restrictions.eq("author.name", user);

        return findByCriteria(offset, limit, order, eqCriteria);
    }
}