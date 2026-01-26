package no.hvl.dat109.spring_stigespill.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat109.spring_stigespill.model.Brett;
import no.hvl.dat109.spring_stigespill.model.Rute;
import no.hvl.dat109.spring_stigespill.model.Spill;
import no.hvl.dat109.spring_stigespill.model.Spiller;
import no.hvl.dat109.spring_stigespill.model.Terning;
import no.hvl.dat109.spring_stigespill.model.Trekk;
import no.hvl.dat109.spring_stigespill.repository.RuteRepository;
import no.hvl.dat109.spring_stigespill.repository.SpillRepository;
import no.hvl.dat109.spring_stigespill.repository.SpillerRepository;
import no.hvl.dat109.spring_stigespill.repository.TrekkRepository;

/*
 * 1. Henter spillet og bygger brettet fra databasen.
 * 2. Finner aktiv spiller og triller terningen.
 * 3. Håndterer regler:
 * - Må ha 6 for å starte fra rute 1.
 * - 3 seksere på rad sender deg tilbake til start.
 * 4. Beregner posisjon (sjekker stige/slange og "retur" fra 100).
 * 5. Oppretter Trekk og lagrer historikken.
 * 6. Sjekker seier eller bytter tur (6 gir nytt kast).
 * 7. Lagrer alt i databasen.
 */

@Service
public class StigespillService {
	
    @Autowired
    private SpillRepository spillRepository;
    @Autowired
    private SpillerRepository spillerRepository;
    @Autowired
    private RuteRepository ruteRepository;
    @Autowired
    private TrekkRepository trekkRepository;

    private Terning terning = new Terning(); 

    public Spill opprettNyttSpill(List<Spiller> spillere) {
        Spill spill = new Spill(spillere);
        return spillRepository.save(spill);
    }

    @Transactional
    public String spillTur(Long spillId) {

        Spill spill = spillRepository.findById(spillId).orElse(null);

        if (spill == null) {
        	return "Ukjent spill ID";
        }
        if (spill.erFerdig()) {
        	return "Spillet er ferdig!";
        }

        List<Rute> alleRuter = ruteRepository.findAll();
        Brett brett = new Brett(alleRuter);
        
        Spiller spiller = spill.nesteSpiller();
        
        int gammelPlass = spiller.getPosisjon();
        int seksereStart = spiller.getAntallSekserePaaRad();
        int seksere = seksereStart; 

        int kast = terning.trill();
        
        if (kast == 6) {
            seksere++;
        } else {
            seksere = 0;
        }
        
        spiller.setAntallSekserePaaRad(seksere);

        if (seksere == 3) {
            spiller.setPosisjon(1);
            spiller.setAntallSekserePaaRad(0);
            
            avsluttTur(spill, spiller, kast, gammelPlass, 1, true);
            
            return spiller.getNavn() 
                    + ": triller 6 (3. gang!) -> rute 1 (Tilbake til start)";
        }

        if (gammelPlass == 1 && seksere == 0 && seksereStart == 0) {
            
            avsluttTur(spill, spiller, kast, 1, 1, true);
            
            return spiller.getNavn() + ": triller " + kast 
                    + ", rute 1 -> 1. Må ha 6 for å starte.";
        }
        
        if (gammelPlass == 1 && kast == 6) {
            
            avsluttTur(spill, spiller, kast, 1, 1, false);
            
            return spiller.getNavn() + ": triller 6! Ute av start. (Nytt kast!)";
        }

        int landerPaa = gammelPlass + kast;
        
        if (landerPaa > 100) {
            landerPaa = 100 - (landerPaa - 100); 
        }

        int nyPlass = brett.finnDestinasjon(gammelPlass, kast);
        spiller.setPosisjon(nyPlass);

        String ekstra = "";
        
        if (nyPlass > landerPaa) {
            ekstra = " (stige " + landerPaa + " -> " + nyPlass + ")";
        } 
        
        if (nyPlass < landerPaa) {
            ekstra = " (slange " + landerPaa + " -> " + nyPlass + ")";
        }

        String melding;
        
        if (nyPlass == 100) {
            spill.setFerdig(true);
            avsluttTur(spill, spiller, kast, gammelPlass, nyPlass, false);
            
            melding = spiller.getNavn() + ": triller " + kast 
                    + ", rute " + gammelPlass + " -> MÅL!";
        } 
        
        else {
            melding = spiller.getNavn() + ": triller " + kast 
                    + ", rute " + gammelPlass + " -> " + nyPlass + ekstra;

            if (kast == 6) {
                avsluttTur(spill, spiller, kast, gammelPlass, nyPlass, false);
                melding += " (Nytt kast!)";
            } else {
                avsluttTur(spill, spiller, kast, gammelPlass, nyPlass, true);
            }
        }

        return melding;
    }

    private void avsluttTur(Spill spill, Spiller s, 
            int k, int fra, int til, boolean byttTur) {
        
        spill.leggTilTrekk(new Trekk(spill, k, fra, 
                til, s.getNavn(), LocalDateTime.now()));
        
        if (byttTur) {
            spill.setNesteSpillerIndex((spill.getNesteSpillerIndex() + 1) 
                    % spill.getSpillere().size());
        }
        
        spillerRepository.save(s);
        spillRepository.save(spill);
    }
}