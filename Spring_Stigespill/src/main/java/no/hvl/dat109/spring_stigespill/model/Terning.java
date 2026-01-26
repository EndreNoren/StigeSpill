package no.hvl.dat109.spring_stigespill.model;

import java.util.Random;

public class Terning {
	private Random random;
	
	public Terning() {
		this.random= new Random();
	}
	
	public int trill() {
		return random.nextInt(6) + 1;
	}
}
