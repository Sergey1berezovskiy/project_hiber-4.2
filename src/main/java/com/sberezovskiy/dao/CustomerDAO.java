package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Address;
import com.sberezovskiy.entity.Customer;
import com.sberezovskiy.entity.Store;
import org.hibernate.SessionFactory;

public class CustomerDAO extends AbstractDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer createNewCustomer(String firstName, String lastName, String email, Store store, Address address){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setStore(store);
        customer.setActive(true);

        return customer;
    }
}
