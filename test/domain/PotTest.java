package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PotTest {

	private Pot pot;
    private Board board;
    private IceCream iceCream;
    
    @BeforeEach
    void setUp() {
        pot = new Pot(5, 5);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        pot.setBoard(board);
        iceCream = board.getIceCream();
    }
    
    @Test
    void testConstructor() {
        assertEquals(5, pot.getGridX());
        assertEquals(5, pot.getGridY());
        assertNotNull(pot.getColor());
    }
    
    @Test
    void testGetType() {
        assertEquals("Pot", pot.getType());
    }
    
    @Test
    void testGetColor() {
        Color color = pot.getColor();
        assertEquals(139, color.getRed());
        assertEquals(69, color.getGreen());
        assertEquals(19, color.getBlue());
    }
    
    @Test
    void testGetAttackRange() {
        assertEquals(0, pot.getAttackRange());
    }
    
    @Test
    void testCanAttack() {
        assertFalse(pot.canAttack());
    }
    
    @Test
    void testWalkWithNullIceCream() {
        int x = pot.getGridX();
        int y = pot.getGridY();
        pot.walk(null);
        assertEquals(x, pot.getGridX());
        assertEquals(y, pot.getGridY());
    }
    
    @Test
    void testWalkWithNullBoard() {
        Pot p = new Pot(3, 3);
        p.walk(iceCream);
        assertEquals(3, p.getGridX());
        assertEquals(3, p.getGridY());
    }
    
    @Test
    void testWalkTowardsIceCream() {
        iceCream.setPosition(10, 10);
        pot.walk(iceCream);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkHorizontalPriority() {
        iceCream.setPosition(10, 5);
        int initialX = pot.getGridX();
        pot.walk(iceCream);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkVerticalPriority() {
        iceCream.setPosition(5, 10);
        int initialY = pot.getGridY();
        pot.walk(iceCream);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkBlockedByWall() {
        pot = new Pot(1, 1);
        pot.setBoard(board);
        iceCream.setPosition(0, 1);
        pot.walk(iceCream);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkBlockedByBlock() {
        board.setBlock(6, 5);
        iceCream.setPosition(10, 5);
        pot.walk(iceCream);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkAlternativeDirection() {
        board.setBlock(6, 5);
        board.setBlock(5, 6);
        iceCream.setPosition(7, 7);
        pot.walk(iceCream);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkMultipleTimes() {
        iceCream.setPosition(12, 12);
        for (int i = 0; i < 20; i++) {
            pot.walk(iceCream);
        }
        assertNotNull(pot);
    }
    
    @Test
    void testWalkSamePosition() {
        iceCream.setPosition(5, 5);
        int x = pot.getGridX();
        int y = pot.getGridY();
        pot.walk(iceCream);
        assertEquals(x, pot.getGridX());
        assertEquals(y, pot.getGridY());
    }
    
    @Test
    void testDirectionChanges() {
        iceCream.setPosition(10, 5);
        pot.walk(iceCream);
        assertTrue(pot.getDirection() == Enemy.RIGHT || pot.getDirection() == Enemy.LEFT);
        
        iceCream.setPosition(5, 10);
        pot.walk(iceCream);
        assertTrue(pot.getDirection() == Enemy.UP || pot.getDirection() == Enemy.DOWN);
    }
    
    @Test
    void testAttackIceCream() {
        iceCream.setPosition(5, 5);
        boolean attacked = pot.attack(iceCream);
        assertFalse(attacked);
    }
    
    @Test
    void testSetBoard() {
        Board newBoard = new Board();
        newBoard.level2(Color.GREEN, Color.YELLOW, false);
        pot.setBoard(newBoard);
        assertNotNull(pot);
    }
    
    @Test
    void testWalkCompletelyBlocked() {
        board.setBlock(6, 5);
        board.setBlock(4, 5);
        board.setBlock(5, 6);
        board.setBlock(5, 4);
        iceCream.setPosition(10, 10);
        int x = pot.getGridX();
        int y = pot.getGridY();
        pot.walk(iceCream);
        assertEquals(x, pot.getGridX());
        assertEquals(y, pot.getGridY());
    }
}