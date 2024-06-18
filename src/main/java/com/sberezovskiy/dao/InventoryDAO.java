package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDAO extends AbstractDAO<Inventory> {
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
