import java.util.*;

public class stigeSpill {
    public static void main(String[] args) {

        List<Spiller> spillere = Arrays.asList(
                new Spiller("A"),
                new Spiller("B"),
                new Spiller("C")
        );

        Spill spill = new Spill(spillere);
        spill.start();
    }


    static class Terning {
        Random t = new Random();

        int kast() {
            return t.nextInt(6) + 1;
        }
    }

    static class Spiller {
        String navn;
        int posisjon;

        Spiller(String navn) {
            this.navn = navn;
            this.posisjon = 0;
        }
    }

    static class Brett {
        Map<Integer, Integer> hopp = new HashMap<>();

        Brett() {
            hopp.put(2, 15);
            hopp.put(10, 35);
            hopp.put(25, 5);
            hopp.put(98, 10);
            hopp.put(37, 42);
            hopp.put(67, 42);
        }

        int getNextPosisjon(int pos) {
            return hopp.getOrDefault(pos, pos);
        }
    }

    static class Spill {
        Queue<Spiller> spillerKo = new LinkedList<>();
        Brett brett = new Brett();
        Terning terning = new Terning();
        final int VINN_POSISJON = 100;

        Spill(List<Spiller> spillerListe) {
            spillerKo.addAll(spillerListe);
        }

        void start() {
            System.out.println("Starter spillet!");

            while (true) {
                Spiller current = spillerKo.poll();

                int kast = terning.kast();
                int nestePos = current.posisjon + kast;

                if (nestePos > VINN_POSISJON) {
                    System.out.println(current.navn + " kastet " + kast + " og landet på " + current.posisjon);
                } else {
                    int etterHopp = brett.getNextPosisjon(nestePos);

                    if (etterHopp != nestePos) {
                        System.out.println(current.navn + " klatret/falt fra " + nestePos + " til " + etterHopp);
                    }

                    current.posisjon = etterHopp;
                    System.out.println(current.navn + " kastet " + kast + " og landet på " + current.posisjon);
                }

                if (current.posisjon == VINN_POSISJON) {
                    System.out.println("\nGratulerer! " + current.navn + " Vinner!!!");
                    break;
                }

                spillerKo.offer(current);
            }
        }
    }
}


