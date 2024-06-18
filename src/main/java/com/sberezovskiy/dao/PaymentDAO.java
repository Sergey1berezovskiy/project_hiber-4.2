package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentDAO extends AbstractDAO<Payment> {
    public PaymentDAO(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
