package stigespill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StigeSpillTest {

    @Test
    void sjekkVanligFlytt() {
        StigeSpill spill = new StigeSpill();
        // Test 1: Start på 0, kast 4 -> skal ende på 4
        assertEquals(4, spill.flyttSpiller(0, 4));
    }

    @Test
    void sjekkAtManIkkeGårOver100() {
        StigeSpill spill = new StigeSpill();
        // Test 2: Stå på 98, kast 5 -> skal bli stående på 98 (ifølge reglene)
        assertEquals(98, spill.flyttSpiller(98, 5));
    }

    @Test
    void sjekkVinneAkkurat() {
        StigeSpill spill = new StigeSpill();
        // Test 3: Stå på 97, kast 3 -> skal lande på nøyaktig 100
        assertEquals(100, spill.flyttSpiller(97, 3));
    }

}