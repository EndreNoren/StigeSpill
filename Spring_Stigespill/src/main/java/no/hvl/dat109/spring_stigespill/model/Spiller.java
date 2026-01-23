package no.hvl.dat109.spring_stigespill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Spiller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //Long standard for id
	private String navn;
	private int posisjon;
	private int antallSekserePaaRad;
	
	protected Spiller() { //tom konstruktør for jpa
	}
	
	public Spiller(String navn) {
		this.navn=navn;
		this.posisjon=1; 
		this.antallSekserePaaRad=0;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getNavn() {
		return this.navn;
	}
	
	public void setNavn(String nyttNavn) {
		this.navn=nyttNavn;
	}
	
	public int getPosisjon() {
		return this.posisjon;
	}

	public void setPosisjon(int nyPosisjon) {
		this.posisjon=nyPosisjon;
	}
	
	public int getAntallSekserePaaRad() {
		return this.antallSekserePaaRad;
	}
	
	public void setAntallSekserePaaRad(int nyttAntall) {
		this.antallSekserePaaRad=nyttAntall;
	}
}
