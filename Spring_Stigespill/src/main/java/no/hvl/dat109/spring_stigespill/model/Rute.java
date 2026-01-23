package no.hvl.dat109.spring_stigespill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/* inneholder boolean erStige(), erSlange(),
 *  erSpesialRute() for enklere logikk
*/
@Entity
public class Rute {
    @Id
    private int id;
    private int flyttTil;
    
    protected Rute() { //tom konstruktør for database
    }
    public Rute(int id, int flyttTil){
    	this.id=id;
    	this.flyttTil=flyttTil;
    }
    
    public boolean erStige() {
    	return flyttTil > id;
    }
    
    public boolean erSlange() {
    	return flyttTil < id && flyttTil !=0;
    }
    
    public boolean erSpesialRute() {
    	return flyttTil != 0 && flyttTil !=id;
    }
    
    public int getId() {
    	return this.id;
    }
    
    public int getFlyttTil() {
    	return this.flyttTil;
    }
    
    public void setFlyttTil(int flyttTil) {
        this.flyttTil = flyttTil;
    }
    
    //toString??
}