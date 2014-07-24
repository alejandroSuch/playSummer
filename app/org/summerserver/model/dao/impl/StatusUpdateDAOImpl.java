package org.summerserver.model.dao.impl;

import org.summerserver.model.dao.StatusUpdateDAO;
import org.summerserver.model.dao.base.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.summerserver.model.vo.StatusUpdate;

public class StatusUpdateDAOImpl extends GenericHibernateSpringDAOImpl<StatusUpdate, String> implements StatusUpdateDAO {
    public StatusUpdateDAOImpl() {
        super(StatusUpdate.class);
    }
}
