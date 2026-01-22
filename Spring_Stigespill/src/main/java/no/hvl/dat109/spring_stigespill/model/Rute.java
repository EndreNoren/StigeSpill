package no.hvl.dat109.spring_stigespill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Rute {
    @Id
    private int id;
    private int flyttTil;
}