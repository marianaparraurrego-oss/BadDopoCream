package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PotTest {

    @Test
    void potSeInicializaCorrectamente() {
        Pot pot = new Pot(4, 5);

        assertEquals(4, pot.getGridX());
        assertEquals(5, pot.getGridY());
        assertEquals("Pot", pot.getType());
    }

    @Test
    void potNoPuedeAtacar() {
        Pot pot = new Pot(0, 0);

        assertEquals(0, pot.getAttackRange());
        assertFalse(pot.canAttack());
    }

    @Test
    void walk_sinBoard_noFalla() {
        Pot pot = new Pot(1, 1);
        IceCream ice = new IceCream(2, 2, null);

        assertDoesNotThrow(() -> pot.walk(ice));
    }
}
