package de.hska.iwii.db1.jpa;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idBooking")
    private long id;
    @OneToOne
    @JoinColumn(name = "idFlight")
    private Flight flight;
    @OneToOne
    @JoinColumn(name = "idCustomer")
    private Customer customer;
    @Column(name = "bookedSeats")
    private int numBookedSeats = 1;
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "Datum")
    private Date date;
    
    //Causes error? TODO
    //@Id
    public Booking setId(long id) {
        this.id = id;
        return this;
    }
   
    public Booking setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }
    
    public Booking setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
   
    public Booking setNumBookedSeats(int numBookedSeats) {
        this.numBookedSeats = ( numBookedSeats >= 1 ) ? numBookedSeats : 1;
        return this;
    }
    
    public Booking setDate(Date date) {
        this.date = date;
        return this;
    }
    
    public long getId() {
        return id;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public int getNumBookedSeats() {
        return numBookedSeats;
    }
    
    public Date getDate() {
        return date;
    }
}
