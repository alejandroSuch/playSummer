package org.summerserver.model.dao.base.hibernate.impl;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.summerserver.model.dao.base.hibernate.GenericHibernateDAO;

import java.io.Serializable;
import java.util.List;


public class GenericHibernateDAOImpl<T, ID extends Serializable> implements GenericHibernateDAO<T, ID> {
    private Class<T> persistentClass;
    private Session session;

    public GenericHibernateDAOImpl(Class<T> persistentClass, Session session) {
        this.persistentClass = persistentClass;

        this.session = session;
    }

    protected Session getSession() {
        return this.session;
    }

    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) {
        T entity;

        if (lock) {
            entity = (T) this.getSession()
                    .load(this.getPersistentClass(), id,
                            LockMode.UPGRADE);
        } else {
            entity = (T) this.getSession().load(this.getPersistentClass(), id);
        }

        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getById(ID id, boolean lock) {
        T entity;

        if (lock) {
            entity = (T) this.getSession()
                    .get(this.getPersistentClass(), id,
                            LockMode.UPGRADE);
        } else {
            entity = (T) this.getSession().get(this.getPersistentClass(), id);
        }

        return entity;
    }


    public List<T> findAll() { // Delegamos en la API Criteria de Hibernate.

        return this.findByCriteria();
    }

    public List<T> findAllPaginated(Integer firstResult, Integer maxResults, String orderBy) {
        return this.findAll();
    }

    public List<T> findAllPaginated(Integer firstResult, Integer maxResults, String orderBy, Boolean ascending) {
        return this.findAll();
    }

    public List<T> findByExample(T exampleInstance) { // Delegamos en la API Criteria de Hibernate.

        return this.findByCriteria(Example.create(exampleInstance));
    }

    public T makePersistent(T entity) {
        this.getSession().saveOrUpdate(entity);

        return entity;
    }

    public void makeTransient(T entity) {
        this.getSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
        Criteria crit = this.getSession()
                .createCriteria(this.getPersistentClass());

        for (Criterion c : criterion) {
            crit.add(c);
        }

        return crit.list();
    }

    @Override
    public Long countAll() {
        Query query = getSession().createQuery("SELECT COUNT(*) FROM " + this.getPersistentClass().getSimpleName());
        return (Long) query.uniqueResult();
    }

}
