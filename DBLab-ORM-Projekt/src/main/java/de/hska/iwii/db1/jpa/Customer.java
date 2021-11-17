package de.hska.iwii.db1.jpa;

import javax.persistence.*;

@Entity
@Table(name = "Kunde")
public class Customer {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, name="Vorname")
    private String foreName;
    @Column(nullable = false, name="Nachname")
    private String surName;
    @Column(nullable = false, name="E-Mail")
    private String email;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setForeName(String foreName) {
        this.foreName = foreName;
    }
    
    public void setNachName(String surName) {
        this.surName = surName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Id
    public int getId() {
        return id;
    }
    
    public String getForeName() {
        return this.foreName;
    }
    
    public String getSurName() {
        return this.surName;
    }
    
    public String getEmail() {
        return this.email;
    }
}
