package de.hska.iwii.db1.jpa;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Flug")
public class Flight {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, name = "Flugnummer")
    private String num;
    @Column(nullable = false, name = "Startzeit")
    @Temporal(TemporalType.TIME)
    private Time startTime;
    @Column(nullable = false, name = "Startflughafen")
    private String startAirport;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setNum(String num) {
        this.num = num;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    public void setStartAirport(String startAirport) {
        this.startAirport = startAirport;
    }
    
    @Id
    public int getId() {
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
