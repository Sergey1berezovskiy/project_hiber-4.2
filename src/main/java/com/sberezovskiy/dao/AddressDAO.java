package com.sberezovskiy.dao;

import com.sberezovskiy.entity.Address;
import com.sberezovskiy.entity.City;
import org.hibernate.SessionFactory;

public class AddressDAO extends AbstractDAO<Address> {
    public AddressDAO(SessionFactory sessionFactory) {
        super(Address.class, sessionFactory);
    }
    public Address createNewAddress(String address1, String address2, String district, City city, String postalCode, String phone ){
        Address address = new Address();
        address.setAddress(address1);
        address.setAddress2(address2);
        address.setDistrict(district);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setPhone(phone);
        this.save(address);

        return address;
    }
    public Address createNewAddress(String address1, String district, City city, String postalCode, String phone ){
        Address address = new Address();
        address.setAddress(address1);
        address.setDistrict(district);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setPhone(phone);

        return address;
    }
}
