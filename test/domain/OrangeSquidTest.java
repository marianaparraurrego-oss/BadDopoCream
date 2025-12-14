package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrangeSquidTest {

    @Test
    void orangeSquidSeInicializaCorrectamente() {
        OrangeSquid squid = new OrangeSquid(2, 3);

        assertEquals(2, squid.getGridX());
        assertEquals(3, squid.getGridY());
        assertEquals("OrangeSquid", squid.getType());
        assertFalse(squid.isBreakingBlock());
    }

    @Test
    void puedeRomperBloques() {
        OrangeSquid squid = new OrangeSquid(0, 0);

        assertTrue(squid.canBreakBlocks());
        assertEquals(1, squid.getBreakBlocksLine());
        assertTrue(squid.breakBlock(Enemy.RIGHT));
    }

    @Test
    void ataqueSoloPorContacto() {
        OrangeSquid squid = new OrangeSquid(1, 1);

        assertEquals(0, squid.getAttackRange());
    }

    @Test
    void canAttack_cuandoNoRompeBloque_esTrue() {
        OrangeSquid squid = new OrangeSquid(1, 1);

        assertTrue(squid.canAttack());
    }

    @Test
    void walk_sinBoard_noLanzaExcepcion() {
        OrangeSquid squid = new OrangeSquid(1, 1);
        IceCream ice = new IceCream(3, 3, null);

        assertDoesNotThrow(() -> squid.walk(ice));
    }
}
