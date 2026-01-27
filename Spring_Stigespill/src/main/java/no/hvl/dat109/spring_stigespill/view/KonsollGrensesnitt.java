package no.hvl.dat109.spring_stigespill.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import no.hvl.dat109.spring_stigespill.model.Spill;
import no.hvl.dat109.spring_stigespill.model.Spiller;
import no.hvl.dat109.spring_stigespill.view.Grafikk;
import no.hvl.dat109.spring_stigespill.service.StigespillService;

@Component
public class KonsollGrensesnitt implements CommandLineRunner {

    @Autowired
    private StigespillService service;
    
    private Grafikk grafikkPanel;
    private JFrame vindu;

    @Override
    public void run(String... args) throws Exception {
        
    	while(true) {
    		
    	}
    	
        Scanner sc = new Scanner(System.in);
        List<Spiller> spillere = new ArrayList<>();

        System.out.print("Antall spillere: ");
        int antall = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= antall; i++) {
            System.out.print("Navn " + i + ": ");
            spillere.add(new Spiller(sc.nextLine(), "F" + i));
        }

        Spill spill = service.opprettNyttSpill(spillere);
        System.out.println("ID: " + spill.getId());

        System.out.println("1: Manuell, 2: Simuler");
        boolean sim = sc.nextLine().equals("2");

        if (!sim) {
            System.out.println("Enter for tur, q for stopp");
        }

        while (true) {
            if (!sim) {
                if (sc.nextLine().equals("q")) {
                    break;
                }
            } else {
                Thread.sleep(300);
            }

            String res = service.spillTur(spill.getId());
            System.out.println(res);

            if (res.contains("MÅL") || res.contains("ferdig")) {
                break;
            }
        }
    }
    public void visMenyVindu() {
    	menyVindu = new JFrame("Stigespill meny");
    	meny
    }
    
    public void startGrafikk() {
    	
    	System.setProperty("java.awt.headless", "false");
    	
    	JFrame vindu = new JFrame("Stigespillet");
        Grafikk grafikkBrett = new Grafikk();
        vindu.add(grafikkBrett);
        vindu.setSize(630, 650);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setVisible(true);
    }
}