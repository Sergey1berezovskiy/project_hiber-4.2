package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Store;
import org.hibernate.SessionFactory;

public class StoreDAO extends AbstractDAO<Store> {
    public StoreDAO(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }
}
