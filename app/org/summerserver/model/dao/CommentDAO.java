package org.summerserver.model.dao;

import org.summerserver.model.dao.base.hibernate.GenericHibernateDAO;
import org.summerserver.model.vo.Comment;
import org.summerserver.model.vo.StatusUpdate;
import java.util.List;

public interface CommentDAO extends GenericHibernateDAO<Comment, Long>{
    public List<Comment> findCommentsFromStatusUpdate(StatusUpdate statusUpdate);
}
