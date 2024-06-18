package com.sberezovskiy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public abstract class AbstractDAO<T> {

    private final Class<T> clazz;
    private final SessionFactory sessionFactory;

    public AbstractDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getByID(int id){
        return (T) getCurrentSession().get(clazz, id);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<T> getItems(int offset, int count){
        Query<T> tQuery = getCurrentSession()
                .createQuery("FROM " + clazz.getName(), clazz);
        tQuery.setFirstResult(offset);
        tQuery.setMaxResults(count);
        return tQuery.list();
    }

    public List<T> findAll(){
        return getCurrentSession()
                .createQuery("FROM "+ clazz.getName(), clazz)
                .list();
    }
    public T save(final T entity){
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public T update(final T entity){
        getCurrentSession().merge(entity);
        return entity;
    }

    public void delete(final T entity){
        getCurrentSession().remove(entity);
    }

    public void deleteById(final int id){
       final T entity = getByID(id);
       delete(entity);
    }



}
