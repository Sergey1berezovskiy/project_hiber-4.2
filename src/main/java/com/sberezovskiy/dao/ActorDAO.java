package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Actor;
import org.hibernate.SessionFactory;

public class ActorDAO extends AbstractDAO<Actor> {

    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
