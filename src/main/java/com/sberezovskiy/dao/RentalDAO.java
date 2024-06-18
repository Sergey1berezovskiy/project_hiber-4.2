package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class RentalDAO extends AbstractDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getUnreturnedFilm() {
     Query<Rental> rentalQuery = getCurrentSession().createQuery("select r from Rental r where r.returnDate is null", Rental.class);
       rentalQuery.setMaxResults(1);
       return rentalQuery.getSingleResult();

    }
}
