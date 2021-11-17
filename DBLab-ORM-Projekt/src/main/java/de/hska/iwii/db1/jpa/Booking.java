package de.hska.iwii.db1.jpa;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @Column(name = "Flug")
    private Flight flight;
    @ManyToOne
    @Column(name = "Kunde")
    private Customer customer;
    @Column(name = "GebuchtePlaetze")
    private int numBookedSeats = 1;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "Datum")
    private Date date;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public int getNumBookedSeats() {
        return numBookedSeats;
    }
    
    public void setNumBookedSeats(int numBookedSeats) {
        this.numBookedSeats = ( numBookedSeats >= 1 ) ? numBookedSeats : 1;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
}
