package de.hska.iwii.db1.jpa;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    @JoinColumn(name = "idCustomer")
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name="forename")
    private String forename;
    @NotNull
    @Column(name="surname")
    private String surname;
    @NotNull
    @Column(name="email")
    private String email;
    
    public Customer setId(Long id) {
        this.id = id;
        return this;
    }
    
    public Customer setForename(String forename) {
        this.forename = forename;
        return this;
    }
    
    public Customer setSurname(String surname) {
        this.surname = surname;
        return this;
    }
    
    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getForename() {
        return this.forename;
    }
    
    public String getSurname() {
        return this.surname;
    }
    
    public String getEmail() {
        return this.email;
    }
}
