package org.summerserver.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.dao.base.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.summerserver.model.vo.Comment;
import org.summerserver.model.vo.StatusUpdate;

import java.util.List;

public class StatusUpdateDAOImpl extends GenericHibernateSpringDAOImpl<StatusUpdate, Long> implements StatusUpdateDAO {
    public StatusUpdateDAOImpl() {
        super(StatusUpdate.class);
    }

    @Override
    public StatusUpdate findStatusWithComments(Long id) {
        //StatusUpdate statusUpdate = (StatusUpdate)getSession().get(StatusUpdate.class, id);

        StatusUpdate statusUpdate = null;
        SessionFactoryUtils.initDeferredClose(getHibernateTemplate().getSessionFactory());

        try {
            statusUpdate = getById(id, false);
            List<Comment> comments = statusUpdate.getComments();
            statusUpdate.setJsonComments(comments.size() > 0 ? comments : null);
        } finally {
            SessionFactoryUtils.processDeferredClose(getHibernateTemplate().getSessionFactory());
        }

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

    @Override
    public Comment addComment(Long statusUpdateId, Comment comment) {
        StatusUpdate statusUpdate = this.getById(statusUpdateId, false);
        getHibernateTemplate().delete(statusUpdate);
        SessionFactoryUtils.initDeferredClose(getHibernateTemplate().getSessionFactory());
        try {
//        Transaction transaction = getSession().beginTransaction();
//            getHibernateTemplate().merge(statusUpdate);
//            getSession().merge(statusUpdate);
            comment.setStatusUpdate(statusUpdate);
            statusUpdate.addComment(comment);
            this.makePersistent(statusUpdate);
//        transaction.commit();
        } finally {
            SessionFactoryUtils.processDeferredClose(getHibernateTemplate().getSessionFactory());
        }

        return comment;
    }

    @Override
    public Long countAllByUser(String username) {
        Query query = getSession().createQuery("SELECT COUNT(*) FROM " + this.getPersistentClass().getSimpleName() + " WHERE author.name = :name ");
        query.setParameter("name", username);
        return (Long) query.uniqueResult();
    }
}