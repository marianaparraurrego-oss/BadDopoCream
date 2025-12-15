package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class NarvalTest {
    
    private Narval narval;
    private Board board;
    private IceCream iceCream;
    
    @BeforeEach
    void setUp() {
        narval = new Narval(5, 5);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        narval.setBoard(board);
        iceCream = board.getIceCream();
    }
    
    @Test
    void testConstructor() {
        assertEquals(5, narval.getGridX());
        assertEquals(5, narval.getGridY());
        assertNotNull(narval.getColor());
        assertFalse(narval.isCharging());
        assertEquals(1, narval.getDirection());
    }
    
    @Test
    void testGetType() {
        assertEquals("Narwhal", narval.getType());
    }
    
    @Test
    void testWalkWithNullIceCream() {
        narval.walk(null);
        assertEquals(5, narval.getGridX());
        assertEquals(5, narval.getGridY());
    }
    
    @Test
    void testWalkWithNullBoard() {
        Narval n = new Narval(3, 3);
        n.walk(iceCream);
        assertEquals(3, n.getGridX());
        assertEquals(3, n.getGridY());
    }
    
    @Test
    void testWalkNormalPatrol() {
        for (int i = 0; i < 5; i++) {
            narval.walk(iceCream);
        }
        assertNotNull(narval);
    }
    
    @Test
    void testBreakBlockWhenNotCharging() {
        assertFalse(narval.breakBlock(Narval.UP));
    }
    
    @Test
    void testCanBreakBlocksWhenNotCharging() {
        assertFalse(narval.canBreakBlocks());
    }
    
    @Test
    void testGetBreakBlocksLine() {
        assertEquals(Integer.MAX_VALUE, narval.getBreakBlocksLine());
    }
    
    @Test
    void testGetAttackRange() {
        assertEquals(0, narval.getAttackRange());
    }
    
    @Test
    void testCanAttack() {
        assertFalse(narval.canAttack());
    }
    
    @Test
    void testIsChargingInitially() {
        assertFalse(narval.isCharging());
    }
    
    @Test
    void testColor() {
        Color color = narval.getColor();
        assertEquals(135, color.getRed());
        assertEquals(206, color.getGreen());
        assertEquals(250, color.getBlue());
    }
    
    @Test
    void testMultipleWalks() {
        for (int i = 0; i < 25; i++) {
            narval.walk(iceCream);
        }
        assertNotNull(narval);
    }
}