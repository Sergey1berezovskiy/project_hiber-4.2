package com.sberezovskiy;

import com.sberezovskiy.dao.*;
import com.sberezovskiy.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Runner {
    private final MySessionFactory mySessionFactory;

    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;


    public Runner(MySessionFactory mySessionFactory) {
        this.mySessionFactory = mySessionFactory;

        actorDAO = new ActorDAO(getSessionFactory());
        addressDAO = new AddressDAO(getSessionFactory());
        categoryDAO = new CategoryDAO(getSessionFactory());
        cityDAO = new CityDAO(getSessionFactory());
        countryDAO = new CountryDAO(getSessionFactory());
        customerDAO = new CustomerDAO(getSessionFactory());
        filmDAO = new FilmDAO(getSessionFactory());
        filmTextDAO = new FilmTextDAO(getSessionFactory());
        inventoryDAO = new InventoryDAO(getSessionFactory());
        languageDAO = new LanguageDAO(getSessionFactory());
        paymentDAO = new PaymentDAO(getSessionFactory());
        rentalDAO = new RentalDAO(getSessionFactory());
        staffDAO = new StaffDAO(getSessionFactory());
        storeDAO = new StoreDAO(getSessionFactory());
    }

    private SessionFactory getSessionFactory() {
        return mySessionFactory.getSessionFactory();
    }

    public static void main(String[] args) {
        MySessionFactory mySessionFactory1 = new MySessionFactory();
        Runner runner = new Runner(mySessionFactory1);
        Customer customer = runner.createCustomer();
        runner.returnFilmToStore();
        runner.customerRentInventory(customer);
        runner.newFilmWasMade();

    }

    private void newFilmWasMade() {
        try(Session session = getSessionFactory().getCurrentSession()){
            session.beginTransaction();

            Language language = languageDAO.getItems(0,20).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 20);
            Film film = new Film();
            setFilm(film, actors, language, categories);
            filmDAO.save(film);
            FilmText filmText = new FilmText();
            setFilmText(filmText, film);
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }

    }

    private static void setFilmText(FilmText filmText, Film film) {
        filmText.setFilm(film);
        filmText.setId(film.getId());
        filmText.setDescription("New Adult Movie");
        filmText.setTitle("Comedy");
    }

    private static void setFilm(Film film, List<Actor> actors, Language language, List<Category> categories) {
        film.setActorList(new HashSet<>(actors));
        film.setRating(Rating.R);
        film.setFeatures(Set.of(Feature.TRAILERS));
        film.setLength((short) 200);
        film.setReplacementCost(BigDecimal.TEN);
        film.setRentalRate(BigDecimal.ONE);
        film.setLanguage(language);
        film.setDescription("New Adult Film");
        film.setTitle("Comedy");
        film.setRentalDuration((byte) 20);
        film.setOriginalLanguage(language);
        film.setFilmCategory(new HashSet<>(categories));
        film.setReleaseYear(Year.now());
    }

    private void customerRentInventory(Customer customer) {
        try(Session session = getSessionFactory().getCurrentSession()){
            session.beginTransaction();
            Film film = filmDAO.getFirstAvailableFilmForRent();
            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            Store store = storeDAO.getItems(0, 1).get(0);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getManager();

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(LocalDateTime.now());
            rental.setInventory(inventory);
            rental.setStaff(staff);

            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setAmount(BigDecimal.valueOf(22.55));
            payment.setRental(rental);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    private void returnFilmToStore() {
        try(Session session = getSessionFactory().getCurrentSession()){
            session.beginTransaction();

            Rental rental = rentalDAO.getUnreturnedFilm();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try(Session session = getSessionFactory().getCurrentSession()){
            session.beginTransaction();
            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("Akron");
            Address address = addressDAO
                    .createNewAddress("ina","Zapad", city, "350082", "99999");
            addressDAO.save(address);
            Customer customer = customerDAO
                    .createNewCustomer("Petr", "Petrov","petr@ya.ru", store, address);
            customerDAO.save(customer);

            session.getTransaction().commit();
            return customer;
        }

    }


}
