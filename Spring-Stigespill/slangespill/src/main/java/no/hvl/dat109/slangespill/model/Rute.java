package no.hvl.dat109.slangespill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rute {
    
    @Id
    private int id; // 1-100 [cite: 32]
    
    private int flyttTil; // Nummeret på ruten man lander på ved slange/stige [cite: 31]

    // Husk tom konstruktør (kreves av JPA)
    public Rute() {}

    // Gettere og settere
}