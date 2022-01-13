package de.hska.iwii.db1.jpa;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.sql.Time;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idFlight")
    private long id;
    @NotNull
    @Column(name = "flightNumber")
    private String num;
    @NotNull
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Time startTime;
    @NotNull
    @Column(name = "startAirport")
    private String startAirport;
    
    public Flight setId(long id) {
        this.id = id;
        return this;
    }
    
    public Flight setNum(String num) {
        this.num = num;
        return this;
    }
    
    public Flight setStartTime(Time startTime) {
        this.startTime = startTime;
        return this;
    }
    
    public Flight setStartAirport(String startAirport) {
        this.startAirport = startAirport;
        return this;
    }
    
    @Id
    public long getId() {
        return id;
    }
    
    public String getNum() {
        return num;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public String getStartAirport() {
        return startAirport;
    }
}
