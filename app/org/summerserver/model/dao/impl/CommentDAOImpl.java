package org.summerserver.model.dao.impl;

import org.summerserver.model.dao.CommentDAO;
import org.summerserver.model.dao.base.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.summerserver.model.vo.Comment;

/**
 * Created by asuch on 24/07/2014.
 */
public class CommentDAOImpl extends GenericHibernateSpringDAOImpl<Comment, String> implements CommentDAO {
    public CommentDAOImpl() {
        super(Comment.class);
    }


}
