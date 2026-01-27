package no.hvl.dat109.spring_stigespill.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.springframework.core.io.ClassPathResource;

import no.hvl.dat109.spring_stigespill.model.Spiller;

public class Grafikk extends JPanel {

    final int RUTE_STR = 60;
    final int ANTALL_RUTER = 10;
    final int BRETT_STR = RUTE_STR * ANTALL_RUTER;

    private List<Spiller> spillere = new ArrayList<>();
    private Image bakgrunnsBilde;

    public Grafikk() {
        try {
            // Spring Boot måte å laste bilder fra resources-mappen
            bakgrunnsBilde = ImageIO.read(new ClassPathResource("Brett.png").getInputStream());
        } catch (IOException e) {
            System.out.println("⚠️ Fant ikke Brett.png i src/main/resources!");
            e.printStackTrace();
        }
    }

    public void oppdaterSpillere(List<Spiller> nyListe) {
        // Lager en kopi av listen for å unngå problemer med samtidighet
        this.spillere = new ArrayList<>(nyListe);
        repaint(); // Tegn på nytt
    }

    private Point getKoordinater(int posisjon) {
        if (posisjon <= 0) return new Point(20, 560);

        int p = posisjon - 1;
        int radFraBunn = p / 10;
        int skjermRad = 9 - radFraBunn;
        int kolonne = p % 10;

        // Slange-logikk (annenhver rad går baklengs)
        if (radFraBunn % 2 != 0) {
            kolonne = 9 - kolonne;
        }
        return new Point(kolonne * RUTE_STR, skjermRad * RUTE_STR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Tegn bakgrunn
        if (bakgrunnsBilde != null) {
            g.drawImage(bakgrunnsBilde, 0, 0, BRETT_STR, BRETT_STR, this);
        }

        // 2. Tegn rutenett (hjelpelinjer)
        g.setColor(Color.BLACK);
        for (int rad = 0; rad < ANTALL_RUTER; rad++) {
            for (int kol = 0; kol < ANTALL_RUTER; kol++) {
                g.drawRect(kol * RUTE_STR, rad * RUTE_STR, RUTE_STR, RUTE_STR);
            }
        }

        // 3. Tegn spillere
        for (Spiller s : spillere) {
            Point p = getKoordinater(s.getPosisjon());

            // Velg farge basert på F-kode (F1, F2...) eller navn
            String farge = s.getFarge(); 
            if ("F1".equals(farge)) g.setColor(Color.RED);
            else if ("F2".equals(farge)) g.setColor(Color.BLUE);
            else if ("F3".equals(farge)) g.setColor(Color.GREEN);
            else if ("F4".equals(farge)) g.setColor(Color.YELLOW);
            else g.setColor(Color.MAGENTA); // Standardfarge

            g.fillOval(p.x + 10, p.y + 10, 40, 40);

            // Svart kant rundt brikken
            g.setColor(Color.BLACK);
            g.drawOval(p.x + 10, p.y + 10, 40, 40);
            
            // Tegn navn over brikken (valgfritt)
            g.drawString(s.getNavn(), p.x + 10, p.y);
        }
    }
}