package stigespill;

import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        JFrame vindu = new JFrame("Stigespillet");
        Grafikk grafikkBrett = new Grafikk();
        vindu.add(grafikkBrett);
        vindu.setSize(630, 650);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setVisible(true);

        List<Spiller> spillere = Arrays.asList(
                new Spiller("A"),
                new Spiller("B"),
                new Spiller("C")
        );

        Spill spill = new Spill(spillere, grafikkBrett);

        new Thread(() -> spill.start()).start();
    }

    static class Spiller {
        String navn;
        int posisjon;

        Spiller(String n) {
            navn = n;
            posisjon = 0;
        }
    }

    static class Spill {
        Queue<Spiller> ko = new LinkedList<>();
        Grafikk grafikk;
        Random terning = new Random();
        Map<Integer, Integer> hopp = new HashMap<>();

        Spill(List<Spiller> liste, Grafikk g) {
            ko.addAll(liste);
            this.grafikk = g;

            // Stiger
            hopp.put(4, 37);
            hopp.put(12, 49);
            hopp.put(27, 55);
            hopp.put(42, 80);
            hopp.put(57, 85);
            hopp.put(67, 94);

            // Slanger
            hopp.put(31, 14);
            hopp.put(36, 7);
            hopp.put(43, 2);
            hopp.put(89, 48);
            hopp.put(93, 76);
            hopp.put(98, 58);

            grafikk.oppdaterSpillere(new ArrayList<>(ko));
        }

        void start() {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            while (true) {
                Spiller s = ko.poll();
                int kast = terning.nextInt(6) + 1;
                System.out.println("🎲 " + s.navn + " kastet " + kast + " og lander på " + s.posisjon);

                int retning = 1;

                for (int i = 0; i < kast; i++) {
                    s.posisjon += retning;

                    if (s.posisjon == 100) {
                        retning = -1; // Snu retning ved 100
                    }

                    oppdaterGrafikk(s);
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                    }
                }

                // Sjekk stiger og slanger
                if (hopp.containsKey(s.posisjon)) {
                    int nyPos = hopp.get(s.posisjon);
                    System.out.println("🐍🪜 " + s.navn + " traff en stige/slange! Hopper fra " + s.posisjon + " til " + nyPos);

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }

                    s.posisjon = nyPos;

                    oppdaterGrafikk(s);
                }

                if (s.posisjon == 100) {
                    JOptionPane.showMessageDialog(null, "Gratulerer! " + s.navn + " vant!");
                    break;
                }

                ko.offer(s);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }

        // Hjelpemetoden
        void oppdaterGrafikk(Spiller s) {
            List<Spiller> listeForTegning = new ArrayList<>(ko);
            listeForTegning.add(s);
            grafikk.oppdaterSpillere(listeForTegning);
        }
    }
}
