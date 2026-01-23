package no.hvl.dat109.spring_stigespill.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Spill {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="spill_id")
	private List<Spiller> spillere = new ArrayList<>();
	
	@OneToMany(mappedBy = "spill", cascade = CascadeType.ALL)
	private List<Trekk> trekkLogg = new ArrayList<>();
	
	private int nesteSpillerIndex;
	private boolean erFerdig;
	
	protected Spill() {} // tom konstruktør for jpa
	
	public Spill(List<Spiller> spillere){
		this.spillere =spillere;
		this.nesteSpillerIndex=0;
		this.erFerdig=false;
	}
	
	//flere meoder..

}
