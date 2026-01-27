package no.hvl.dat109.spring_stigespill.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import no.hvl.dat109.spring_stigespill.model.Spill;
import no.hvl.dat109.spring_stigespill.model.Spiller;
import no.hvl.dat109.spring_stigespill.service.StigespillService;
import no.hvl.dat109.spring_stigespill.view.BrettPanel;

/**
 * Kontroller som styrer flyten i spillet.
 * Klassen setter opp det grafiske vinduet, håndterer input fra konsollen,
 * og kommuniserer mellom brukeren og spill-tjenesten.
 */
@Component
public class SpillController implements CommandLineRunner {

    @Autowired
    private StigespillService service;

    private BrettPanel brettPanel;

    /**
     * Start-metoden som kjøres når applikasjonen starter.
     * Setter opp GUI, henter spillere og kjører hovedløkken for spillet.
     * * @param args Argumenter fra kommandolinjen (ikke i bruk).
     */
    @Override
    public void run(String... args) {
        System.setProperty("java.awt.headless", "false");

        JFrame vindu = new JFrame("Stigespill");
        brettPanel = new BrettPanel();
        vindu.add(brettPanel);
        vindu.setSize(620, 650);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setResizable(false);
        vindu.setVisible(true);

        Scanner sc = new Scanner(System.in);
        
        System.out.println("--- NYTT SPILL ---");
        System.out.print("Antall spillere: ");
        int antall = Integer.parseInt(sc.nextLine());

        List<Spiller> spillere = new ArrayList<>();
        for (int i = 1; i <= antall; i++) {
            System.out.print("Navn spiller " + i + ": ");
            spillere.add(new Spiller(sc.nextLine(), "F" + i));
        }

        Spill spill = service.opprettNyttSpill(spillere);
        brettPanel.oppdaterSpillere(spill.getSpillere());

        System.out.println("Spill opprettet (ID: " + spill.getId() + ")");
        System.out.println("Trykk ENTER for å trille. 'q' for å avslutte.");

        while (true) {
            String input = sc.nextLine();
            
            if ("q".equals(input)) {
                System.out.println("Avslutter...");
                break;
            }

            String melding = service.spillTur(spill.getId());
            System.out.println(melding);

            Spill oppdatert = service.hentSpill(spill.getId());
            brettPanel.oppdaterSpillere(oppdatert.getSpillere());

            if (oppdatert.erFerdig()) {
                System.out.println("--- SPILLET ER SLUTT ---");
                break;
            }
        }
        
        sc.close();
    }
}