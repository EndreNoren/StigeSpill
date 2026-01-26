package no.hvl.dat109.spring_stigespill.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brett {
	
	private final Map <Integer, Rute> ruter = new HashMap<>();

	public Brett (List<Rute> alleRuter) {
		for(Rute r : alleRuter) {
			this.ruter.put(r.getId(), r);
		}
	}
	
	public int finnDestinasjon(int plassering, int kast) {
		int nyPlassering = plassering + kast;
		
		if(nyPlassering > 100) {
			int overskytende = nyPlassering -100;
			nyPlassering = 100 - overskytende;
		}
		
		Rute landerPaa = ruter.get(nyPlassering);
		
		if(landerPaa != null && landerPaa.erSpesialRute()) {
			return landerPaa.getFlyttTil();
		}
		return nyPlassering;
	}
	
	public Rute hentRute(int nr) {
		return ruter.get(nr);
	}
}
