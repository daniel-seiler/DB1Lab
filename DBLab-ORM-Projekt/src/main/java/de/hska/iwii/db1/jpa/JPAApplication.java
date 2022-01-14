package de.hska.iwii.db1.jpa;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAApplication {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;
    
    public JPAApplication() {
        Logger.getLogger("org.hibernate").setLevel(Level.ALL);
        entityManagerFactory = Persistence.createEntityManagerFactory("DB1");
        this.em = this.entityManagerFactory.createEntityManager();
    }
    
    public void testFlights() {
        Customer c1 = addCustomer(0, "Margarete", "Eisenhardt", "mega.eisenhardt@web.de");
        Customer c2 = addCustomer(1, "Dorothea", "Krasnewicz", "doro.krasnewicz@yahoo.com");
        Flight f1 = addFlight(0, "NER236", new Date(12039493), "Guangzhou Baiyun International Airport");
        Flight f2 = addFlight(1, "RHN563", new Date(190587), "Bad Kissing Airfield");
        Flight f3 = addFlight(2, "ABC109", new Date(192385), "Fak Fak Airport");
        Booking b1 = addBooking(0, f1, c1, 2, new Date(298346));
        Booking b2 = addBooking(1, f2, c1, 2, new Date(985341));
        Booking b3 = addBooking(2, f2, c2, 2, new Date(725834));
        Booking b4 = addBooking(3, f3, c2, 2, new Date(569348));
        
        String customerSurname = "Eisenhardt";
        List<Customer> customerList = em.createQuery("FROM Customer", Customer.class).getResultList();
        Customer customer = customerList.stream().filter(c -> c.getSurname().equals(customerSurname)).findFirst().orElse(null);
        List<Booking> bookingList = em.createQuery("FROM Booking", Booking.class).getResultList();
        for (Booking tempBooking: bookingList) {
            if (tempBooking.getCustomer().getId() == customer.getId()) {
                System.out.println(tempBooking.getId());
            }
        }
        em.close();
    }
    
    public Flight addFlight(long id, String flightNumber, Date startTime, String startAirport) {
        Flight flight = new Flight()
                .setNum(flightNumber)
                .setStartTime(startTime)
                .setStartAirport(startAirport);
        em.getTransaction().begin();
        em.persist(flight);
        em.getTransaction().commit();
        return flight;
    }
    
    public Customer addCustomer(long id, String forename, String surname, String email) {
        Customer customer = new Customer()
                .setForename(forename)
                .setSurname(surname)
                .setEmail(email);
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        return customer;
    }
    
    public Booking addBooking(long id, Flight flight, Customer customer, int numBookedSeats, Date date) {
        Booking booking = new Booking()
                .setFlight(flight)
                .setCustomer(customer)
                .setNumBookedSeats(numBookedSeats)
                .setDate(date);
        em.getTransaction().begin();
        em.persist(booking);
        em.getTransaction().commit();
        return booking;
    }
    
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    public static void main(String[] args) {
        JPAApplication app = new JPAApplication();
        app.testFlights();
        app.getEntityManagerFactory().close();
    }
}
