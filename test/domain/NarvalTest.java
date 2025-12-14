package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NarvalTest {

    @Test
    void narvalSeInicializaCorrectamente() {
        Narval narval = new Narval(3, 4);

        assertEquals(3, narval.getGridX());
        assertEquals(4, narval.getGridY());
        assertEquals("Narwhal", narval.getType());
        assertFalse(narval.isCharging());
    }

    @Test
    void breakBlock_soloCuandoEstaCargando() {
        Narval narval = new Narval(1, 1);

        assertFalse(narval.breakBlock(Enemy.RIGHT));
    }

    @Test
    void canBreakBlocks_dependeDeEmbestida() {
        Narval narval = new Narval(2, 2);
        assertFalse(narval.canBreakBlocks());
    }
}
