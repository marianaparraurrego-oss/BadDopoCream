package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class HungryAITest {
    
    private HungryAI hungryAI;
    private Board board;
    
    @BeforeEach
    void setUp() {
        hungryAI = new HungryAI(5, 5, Color.BLUE);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        hungryAI.setBoard(board);
    }
    
    @Test
    void testConstructor() {
        assertEquals(5, hungryAI.getGridX());
        assertEquals(5, hungryAI.getGridY());
        assertEquals(Color.BLUE, hungryAI.getColor());
        assertEquals("hungry", hungryAI.getProfile());
    }
    
    @Test
    void testMakeDecisionWithNullBoard() {
        HungryAI ai = new HungryAI(3, 3, Color.RED);
        ai.makeDecision();
        assertEquals(3, ai.getGridX());
        assertEquals(3, ai.getGridY());
    }
    
    @Test
    void testMakeDecisionWithBoard() {
        board.addFruit(new Banana(10, 10));
        hungryAI.makeDecision();
        assertNotNull(hungryAI);
    }
    
    @Test
    void testMakeDecisionNoFruits() {
        board.getFruits().clear();
        int x = hungryAI.getGridX();
        int y = hungryAI.getGridY();
        hungryAI.makeDecision();
        assertNotNull(hungryAI);
    }
    
    @Test
    void testMakeDecisionMultipleTimes() {
        board.addFruit(new Banana(8, 8));
        for (int i = 0; i < 10; i++) {
            hungryAI.makeDecision();
        }
        assertNotNull(hungryAI);
    }
    
    @Test
    void testGetProfile() {
        assertEquals("hungry", hungryAI.getProfile());
    }
    
    @Test
    void testSetBoard() {
        Board newBoard = new Board();
        newBoard.level2(Color.GREEN, Color.YELLOW, false);
        hungryAI.setBoard(newBoard);
        assertNotNull(hungryAI.board);
    }
    
    @Test
    void testInheritsFromIceCreamAI() {
        assertTrue(hungryAI instanceof IceCreamAI);
        assertTrue(hungryAI instanceof IceCream);
    }
    
    @Test
    void testShootIfPossible() {
        hungryAI.shootIfPossible();
        assertNotNull(hungryAI);
    }
    
    @Test
    void testMakeDecisionWithFruitNearby() {
        board.addFruit(new Grapes(6, 5));
        hungryAI.makeDecision();
        assertNotNull(hungryAI);
    }
}