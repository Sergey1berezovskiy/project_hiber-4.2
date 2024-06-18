package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends AbstractDAO<Film> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {

        Query<Film> filmQuery = getCurrentSession().createQuery("select f from Film f " +
                "where f.id not in (select distinct film.id from Inventory)", Film.class);

        filmQuery.setMaxResults(1);
        return filmQuery.getSingleResult();

    }
}
