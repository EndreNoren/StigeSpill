package stigespill.versjon2;

import javax.swing.*;

import stigespill.versjon2.Main.Spiller;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.io.IOException;
import javax.imageio.ImageIO;


// --- GRAFIKK-KLASSEN ---
class Grafikk extends JPanel {

    final int RUTE_STR = 60;
    final int ANTALL_RUTER = 10;
    // Regner ut hvor stort bildet skal tegnes (60 * 10 = 600 piksler)
    final int BRETT_STR = RUTE_STR * ANTALL_RUTER;

    List<Main.Spiller> spillere = new ArrayList<>();

    // Variabel for å lagre bildet
    Image bakgrunnsBilde;

    // KONSTRUKTØR: Laster inn bildet når Grafikk opprettes
    Grafikk() {
        try {
            java.net.URL bildeURL = getClass().getResource("/Brett.png");

            if (bildeURL != null) {
                bakgrunnsBilde = ImageIO.read(bildeURL);
            } else {
                System.out.println("⚠️ Fant ikke bildet! Sjekk at Brett.png ligger inni src-mappen.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oppdaterSpillere(List<Main.Spiller> nyListe) {
        this.spillere = new ArrayList<>(nyListe);
        repaint();
    }

    Point getKoordinater(int posisjon) {
        if (posisjon <= 0) return new Point(20, 560);

        int p = posisjon - 1;
        int radFraBunn = p / 10;
        int skjermRad = 9 - radFraBunn;
        int kolonne = p % 10;

        if (radFraBunn % 2 != 0) {
            kolonne = 9 - kolonne;
        }
        return new Point(kolonne * RUTE_STR, skjermRad * RUTE_STR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. TEGN BAKGRUNNSBILDET FØRST
        if (bakgrunnsBilde != null) {
            // Tegn bildet slik at det dekker hele brettet (0,0 til 600,600)
            g.drawImage(bakgrunnsBilde, 0, 0, BRETT_STR, BRETT_STR, this);
        }

        // 2. TEGN RUTENETT OG TALL (Oppå bildet)
        for (int rad = 0; rad < ANTALL_RUTER; rad++) {
            for (int kol = 0; kol < ANTALL_RUTER; kol++) {
                int x = kol * RUTE_STR;
                int y = rad * RUTE_STR;

                // NB: Vi fjernet g.fillRect(Color.WHITE) her,
                // slik at vi ser bildet gjennom rutene!

                // Tegn svarte streker så vi ser hvor rutene er
                g.setColor(Color.BLACK);
                g.drawRect(x, y, RUTE_STR, RUTE_STR);

                int radFraBunn = 9 - rad;
                int tall;
                if (radFraBunn % 2 == 0) tall = (radFraBunn * 10) + kol + 1;
                else tall = (radFraBunn * 10) + (9 - kol) + 1;

                // Tegn tallene
                //g.setColor(Color.BLACK);
                // Tips: Hvis bildet ditt er mørkt, endre linjen over til Color.WHITE
                //g.drawString("" + tall, x + 5, y + 20);
            }
        }

        // 3. TEGN SPILLERE
        for (Main.Spiller s : spillere) {
            Point p = getKoordinater(s.posisjon);

            if (s.navn.equals("A")) g.setColor(Color.RED);
            else if (s.navn.equals("B")) g.setColor(Color.BLUE);
            else g.setColor(Color.GREEN);

            g.fillOval(p.x + 10, p.y + 10, 40, 40);

            // (Valgfritt) Tegn en svart kant rundt brikken så den synes bedre
            g.setColor(Color.BLACK);
            g.drawOval(p.x + 10, p.y + 10, 40, 40);
        }
    }
}