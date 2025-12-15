package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrangeSquidTest {

	 private OrangeSquid squid;
	    private Board board;
	    private IceCream iceCream;
	    
	    @BeforeEach
	    void setUp() {
	        squid = new OrangeSquid(5, 5);
	        board = new Board();
	        board.level1(Color.BLUE, Color.RED, false);
	        squid.setBoard(board);
	        iceCream = board.getIceCream();
	    }
	    
	    @Test
	    void testConstructor() {
	        assertEquals(5, squid.getGridX());
	        assertEquals(5, squid.getGridY());
	        assertNotNull(squid.getColor());
	        assertFalse(squid.isBreakingBlock());
	    }
	    
	    @Test
	    void testGetType() {
	        assertEquals("OrangeSquid", squid.getType());
	    }
	    
	    @Test
	    void testGetColor() {
	        Color color = squid.getColor();
	        assertEquals(255, color.getRed());
	        assertEquals(140, color.getGreen());
	        assertEquals(0, color.getBlue());
	    }
	    
	    @Test
	    void testBreakBlock() {
	        assertTrue(squid.breakBlock(Enemy.UP));
	    }
	    
	    @Test
	    void testCanBreakBlocks() {
	        assertTrue(squid.canBreakBlocks());
	    }
	    
	    @Test
	    void testGetBreakBlocksLine() {
	        assertEquals(1, squid.getBreakBlocksLine());
	    }
	    
	    @Test
	    void testGetAttackRange() {
	        assertEquals(0, squid.getAttackRange());
	    }
	    
	    @Test
	    void testCanAttack() {
	        assertTrue(squid.canAttack());
	    }
	    
	    @Test
	    void testCanAttackWhileBreaking() {
	        board.setBlock(6, 5);
	        iceCream.setPosition(10, 5);
	        squid.walk(iceCream);
	        assertFalse(squid.canAttack());
	    }
	    
	    @Test
	    void testWalkWithNullIceCream() {
	        int x = squid.getGridX();
	        int y = squid.getGridY();
	        squid.walk(null);
	        assertEquals(x, squid.getGridX());
	        assertEquals(y, squid.getGridY());
	    }
}
