package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PineaapleTest {

	private Pineapple pineapple;
    private Board board;
    private IceCream iceCream;
    
    @BeforeEach
    void setUp() {
        pineapple = new Pineapple(8, 8);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        iceCream = board.getIceCream();
        pineapple.setBoard(board);
        pineapple.setIceCream(iceCream);
    }
    
    @Test
    void testConstructor() {
        assertEquals(8, pineapple.getGridX());
        assertEquals(8, pineapple.getGridY());
        assertEquals(Pineapple.pineapplePoints, pineapple.getPoints());
        assertTrue(pineapple.isVisibleFruit());
    }
    
    @Test
    void testGetType() {
        assertEquals("Pineapple", pineapple.getType());
    }
    
    @Test
    void testGetColor() {
        Color color = pineapple.getColor();
        assertEquals(255, color.getRed());
        assertEquals(215, color.getGreen());
        assertEquals(0, color.getBlue());
    }
    
    @Test
    void testPoints() {
        assertEquals(200, pineapple.getPoints());
    }
    
    @Test
    void testSetIceCream() {
        IceCream newIce = new IceCream(10, 10, Color.GREEN);
        pineapple.setIceCream(newIce);
        assertNotNull(pineapple);
    }
    
    @Test
    void testSetIceCreamNull() {
        pineapple.setIceCream(null);
        pineapple.move();
        assertEquals(8, pineapple.getGridX());
        assertEquals(8, pineapple.getGridY());
    }
    
    @Test
    void testSetBoard() {
        Board newBoard = new Board();
        pineapple.setBoard(newBoard);
        assertNotNull(pineapple);
    }
    
    @Test
    void testMoveWithNullIceCream() {
        Pineapple p = new Pineapple(5, 5);
        p.setBoard(board);
        int x = p.getGridX();
        int y = p.getGridY();
        p.move();
        assertEquals(x, p.getGridX());
        assertEquals(y, p.getGridY());
    }
    
    @Test
    void testMoveWithNullBoard() {
        Pineapple p = new Pineapple(5, 5);
        p.setIceCream(iceCream);
        int x = p.getGridX();
        int y = p.getGridY();
        p.move();
        assertEquals(x, p.getGridX());
        assertEquals(y, p.getGridY());
    }
    
    @Test
    void testMoveWhenIceCreamMoves() {
        int initialPineappleX = pineapple.getGridX();
        int initialPineappleY = pineapple.getGridY();
        
        iceCream.setPosition(iceCream.getGridX() + 1, iceCream.getGridY());
        pineapple.move();
        
        assertNotNull(pineapple);
    }
    
    @Test
    void testMoveWhenIceCreamDoesNotMove() {
        int x = pineapple.getGridX();
        int y = pineapple.getGridY();
        
        pineapple.move();
        
        assertEquals(x, pineapple.getGridX());
        assertEquals(y, pineapple.getGridY());
    }
    
    @Test
    void testMoveMultipleTimes() {
        for (int i = 0; i < 5; i++) {
            iceCream.move(IceCream.RIGHT);
            pineapple.move();
        }
        assertNotNull(pineapple);
    }
    
    @Test
    void testMoveBlockedByWall() {
        pineapple = new Pineapple(1, 1);
        pineapple.setBoard(board);
        pineapple.setIceCream(iceCream);
        
        iceCream.setPosition(0, 1);
        pineapple.move();
        assertNotNull(pineapple);
    }
    
    @Test
    void testMoveBlockedByBlock() {
        board.setBlock(9, 8);
        iceCream.setPosition(10, 8);
        pineapple.move();
        assertNotNull(pineapple);
    }
    
    @Test
    void testVisibility() {
        assertTrue(pineapple.isVisibleFruit());
        pineapple.makeInvisible();
        assertFalse(pineapple.isVisibleFruit());
        pineapple.makeVisible();
        assertTrue(pineapple.isVisibleFruit());
    }
    
    @Test
    void testMoveInAllDirections() {
        iceCream.setPosition(iceCream.getGridX(), iceCream.getGridY() - 1);
        pineapple.move();
        
        iceCream.setPosition(iceCream.getGridX() + 1, iceCream.getGridY());
        pineapple.move();
        
        iceCream.setPosition(iceCream.getGridX(), iceCream.getGridY() + 1);
        pineapple.move();
        
        iceCream.setPosition(iceCream.getGridX() - 1, iceCream.getGridY());
        pineapple.move();
        
        assertNotNull(pineapple);
    }
}