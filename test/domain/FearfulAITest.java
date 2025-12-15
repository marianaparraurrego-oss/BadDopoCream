package domain;


import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FearfulAITest {

	 private FearfulAI fearfulAI;
	    private Board board;
	    
	    @BeforeEach
	    void setUp() {
	        fearfulAI = new FearfulAI(8, 8, Color.GREEN);
	        board = new Board();
	        board.level1(Color.BLUE, Color.RED, false);
	        fearfulAI.setBoard(board);
	    }
	    
	    @Test
	    void testConstructor() {
	        assertEquals(8, fearfulAI.getGridX());
	        assertEquals(8, fearfulAI.getGridY());
	        assertEquals(Color.GREEN, fearfulAI.getColor());
	        assertEquals("fearful", fearfulAI.getProfile());
	    }
	    
	    @Test
	    void testMakeDecisionWithNullBoard() {
	        FearfulAI ai = new FearfulAI(3, 3, Color.RED);
	        ai.makeDecision();
	        assertEquals(3, ai.getGridX());
	        assertEquals(3, ai.getGridY());
	    }
	    
	    @Test
	    void testMakeDecisionNoEnemies() {
	        board.getEnemies().clear();
	        board.addFruit(new Banana(10, 10));
	        fearfulAI.makeDecision();
	        assertNotNull(fearfulAI);
	    }
	    
	    @Test
	    void testMakeDecisionWithNearbyEnemy() {
	        Enemy enemy = new PatrollingEnemy(9, 9, 1, 15, 1, 15);
	        board.addEnemy(enemy);
	        fearfulAI.makeDecision();
	        assertNotNull(fearfulAI);
	    }
	    
	    @Test
	    void testMakeDecisionWithDistantEnemy() {
	        Enemy enemy = new PatrollingEnemy(2, 2, 1, 15, 1, 15);
	        board.addEnemy(enemy);
	        board.addFruit(new Banana(9, 9));
	        fearfulAI.makeDecision();
	        assertNotNull(fearfulAI);
	    }
	    
	    @Test
	    void testMakeDecisionMultipleTimes() {
	        Enemy enemy = new PatrollingEnemy(10, 10, 1, 15, 1, 15);
	        board.addEnemy(enemy);
	        for (int i = 0; i < 10; i++) {
	            fearfulAI.makeDecision();
	        }
	        assertNotNull(fearfulAI);
	    }
	    
	    @Test
	    void testGetProfile() {
	        assertEquals("fearful", fearfulAI.getProfile());
	    }
	    
	    @Test
	    void testInheritsFromIceCreamAI() {
	        assertTrue(fearfulAI instanceof IceCreamAI);
	        assertTrue(fearfulAI instanceof IceCream);
	    }
	    
	    @Test
	    void testMakeDecisionWithUnsafeFruit() {
	        board.addFruit(new Banana(10, 10));
	        Cactus cactus = new Cactus(10, 10);
	        board.addFruit(cactus);
	        fearfulAI.makeDecision();
	        assertNotNull(fearfulAI);
	    }
	}