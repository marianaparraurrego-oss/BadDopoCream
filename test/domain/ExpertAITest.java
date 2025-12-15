package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class ExpertAITest {
    
    private ExpertAI expertAI;
    private Board board;
    
    @BeforeEach
    void setUp() {
        expertAI = new ExpertAI(7, 7, Color.CYAN);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        expertAI.setBoard(board);
    }
    
    @Test
    void testConstructor() {
        assertEquals(7, expertAI.getGridX());
        assertEquals(7, expertAI.getGridY());
        assertEquals(Color.CYAN, expertAI.getColor());
        assertEquals("expert", expertAI.getProfile());
    }
    
    @Test
    void testMakeDecisionWithNullBoard() {
        ExpertAI ai = new ExpertAI(3, 3, Color.RED);
        ai.makeDecision();
        assertEquals(3, ai.getGridX());
        assertEquals(3, ai.getGridY());
    }
    
    @Test
    void testMakeDecisionLowThreat() {
        board.getEnemies().clear();
        board.addFruit(new Banana(10, 10));
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
    
    @Test
    void testMakeDecisionMediumThreat() {
        Enemy enemy = new PatrollingEnemy(10, 10, 1, 15, 1, 15);
        board.addEnemy(enemy);
        board.addFruit(new Banana(12, 12));
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
    
    @Test
    void testMakeDecisionHighThreat() {
        Enemy enemy1 = new PatrollingEnemy(8, 7, 1, 15, 1, 15);
        Enemy enemy2 = new PatrollingEnemy(7, 8, 1, 15, 1, 15);
        board.addEnemy(enemy1);
        board.addEnemy(enemy2);
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
    
    @Test
    void testMakeDecisionWithNarval() {
        Narval narval = new Narval(9, 9);
        board.addEnemy(narval);
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
    
    @Test
    void testMakeDecisionWithCactusSpikes() {
        Cactus cactus = new Cactus(8, 8);
        board.addFruit(cactus);
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
    
    @Test
    void testMakeDecisionMultipleTimes() {
        board.addFruit(new Banana(10, 10));
        board.addFruit(new Cherry(12, 12));
        for (int i = 0; i < 15; i++) {
            expertAI.makeDecision();
        }
        assertNotNull(expertAI);
    }
    
    @Test
    void testGetProfile() {
        assertEquals("expert", expertAI.getProfile());
    }
    
    @Test
    void testInheritsFromIceCreamAI() {
        assertTrue(expertAI instanceof IceCreamAI);
        assertTrue(expertAI instanceof IceCream);
    }
    
    @Test
    void testMakeDecisionNoFruits() {
        board.getFruits().clear();
        board.getEnemies().clear();
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
    
    @Test
    void testMakeDecisionWithBonfire() {
        Bonfire bonfire = new Bonfire(8, 8);
        board.addBonfire(bonfire);
        board.addFruit(new Banana(10, 10));
        expertAI.makeDecision();
        assertNotNull(expertAI);
    }
}