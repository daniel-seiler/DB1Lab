package de.hska.iwii.db1.jpa;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Flight {
    @Id
    @JoinColumn(name = "idFlight")
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name = "flightNumber")
    private String num;
    @NotNull
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @NotNull
    @Column(name = "startAirport")
    private String startAirport;
    
    public Flight setId(Long id) {
        this.id = id;
        return this;
    }
    
    public Flight setNum(String num) {
        this.num = num;
        return this;
    }
    
    public Flight setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }
    
    public Flight setStartAirport(String startAirport) {
        this.startAirport = startAirport;
        return this;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getNum() {
        return num;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public String getStartAirport() {
        return startAirport;
    }
}
