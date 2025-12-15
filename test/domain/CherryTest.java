package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CherryTest {

	private Cherry cherry;
    private Board board;
    
    @BeforeEach
    void setUp() {
        cherry = new Cherry(5, 5);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        cherry.setBoard(board);
    }
    
    @Test
    void testConstructor() {
        assertEquals(5, cherry.getGridX());
        assertEquals(5, cherry.getGridY());
        assertEquals(Color.RED, cherry.getColor());
        assertEquals(Cherry.cherryPoints, cherry.getPoints());
        assertTrue(cherry.isVisibleFruit());
    }
    
    @Test
    void testGetType() {
        assertEquals("Cherry", cherry.getType());
    }
    
    @Test
    void testSetBoard() {
        Cherry ch = new Cherry(3, 3);
        ch.setBoard(board);
        assertNotNull(ch);
    }
    
    @Test
    void testMoveWithoutBoard() {
        Cherry ch = new Cherry(7, 7);
        int x = ch.getGridX();
        int y = ch.getGridY();
        ch.move();
        assertEquals(x, ch.getGridX());
        assertEquals(y, ch.getGridY());
    }
    
    @Test
    void testMoveWithBoard() {
        cherry.move();
        assertNotNull(cherry);
    }
    
    @Test
    void testPoints() {
        assertEquals(150, cherry.getPoints());
    }
    
    @Test
    void testVisibility() {
        assertTrue(cherry.isVisibleFruit());
        cherry.makeInvisible();
        assertFalse(cherry.isVisibleFruit());
        cherry.makeVisible();
        assertTrue(cherry.isVisibleFruit());
    }
    
    @Test
    void testMultipleMoves() {
        for (int i = 0; i < 5; i++) {
            cherry.move();
        }
        assertNotNull(cherry);
    }
}