package com.sberezovskiy.dao;

import com.sberezovskiy.entity.FilmText;
import org.hibernate.SessionFactory;

public class FilmTextDAO extends AbstractDAO<FilmText> {
    public FilmTextDAO(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
