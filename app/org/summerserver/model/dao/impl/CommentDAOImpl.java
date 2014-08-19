package org.summerserver.model.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.summerserver.model.dao.CommentDAO;
import org.summerserver.model.dao.base.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.summerserver.model.vo.Comment;
import org.summerserver.model.vo.StatusUpdate;

import java.util.List;

public class CommentDAOImpl extends GenericHibernateSpringDAOImpl<Comment, Long> implements CommentDAO {
    public CommentDAOImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> findCommentsFromStatusUpdate(StatusUpdate statusUpdate) {
        Criterion eqCriteria = Restrictions.eq("statusUpdate.id", statusUpdate.getId());

        return findByCriteria(eqCriteria);
    }
}
