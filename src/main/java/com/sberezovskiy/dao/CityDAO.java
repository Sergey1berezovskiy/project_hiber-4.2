package com.sberezovskiy.dao;

import com.sberezovskiy.entity.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO extends AbstractDAO<City>{
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        Query<City> cityQuery = getCurrentSession()
                .createQuery("select c from City c where c.city =:name", City.class);
        cityQuery.setParameter("name", name);
        cityQuery.setMaxResults(1);

        return cityQuery.getSingleResult();

    }
}
