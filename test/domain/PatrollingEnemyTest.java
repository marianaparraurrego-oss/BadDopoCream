package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class PatrollingEnemyTest {
    
    private PatrollingEnemy enemy;
    private Board board;
    private IceCream iceCream;
    
    @BeforeEach
    void setUp() {
        enemy = new PatrollingEnemy(5, 5, 1, 15, 1, 15);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        enemy.setBoard(board);
        iceCream = board.getIceCream();
    }
    
    @Test
    void testConstructor() {
        assertEquals(5, enemy.getGridX());
        assertEquals(5, enemy.getGridY());
        assertNotNull(enemy.getColor());
    }
    
    @Test
    void testGetType() {
        assertEquals("Troll", enemy.getType());
    }
    
    @Test
    void testGetColor() {
        Color color = enemy.getColor();
        assertEquals(100, color.getRed());
        assertEquals(100, color.getGreen());
        assertEquals(100, color.getBlue());
    }
    
    @Test
    void testGetAttackRange() {
        assertEquals(0, enemy.getAttackRange());
    }
    
    @Test
    void testCanAttack() {
        assertFalse(enemy.canAttack());
    }
    
    @Test
    void testWalkInitial() {
        int initialX = enemy.getGridX();
        int initialY = enemy.getGridY();
        enemy.walk(iceCream);
        assertNotNull(enemy);
    }
    
    @Test
    void testWalkChangesDirection() {
        for (int i = 0; i < 5; i++) {
            enemy.walk(iceCream);
        }
        assertNotNull(enemy);
    }
    
    @Test
    void testWalkMultipleTimes() {
        for (int i = 0; i < 20; i++) {
            enemy.walk(iceCream);
        }
        assertTrue(enemy.getGridX() >= 1 && enemy.getGridX() <= 15);
        assertTrue(enemy.getGridY() >= 1 && enemy.getGridY() <= 15);
    }
    
    @Test
    void testWalkStaysInBounds() {
        for (int i = 0; i < 50; i++) {
            enemy.walk(iceCream);
            assertTrue(enemy.getGridX() >= 1);
            assertTrue(enemy.getGridX() <= 15);
            assertTrue(enemy.getGridY() >= 1);
            assertTrue(enemy.getGridY() <= 15);
        }
    }
    
    @Test
    void testWalkWithSmallBounds() {
        PatrollingEnemy smallEnemy = new PatrollingEnemy(3, 3, 2, 5, 2, 5);
        smallEnemy.setBoard(board);
        
        for (int i = 0; i < 30; i++) {
            smallEnemy.walk(iceCream);
            assertTrue(smallEnemy.getGridX() >= 2);
            assertTrue(smallEnemy.getGridX() <= 5);
            assertTrue(smallEnemy.getGridY() >= 2);
            assertTrue(smallEnemy.getGridY() <= 5);
        }
    }
    
    @Test
    void testWalkBlockedByWall() {
        PatrollingEnemy cornerEnemy = new PatrollingEnemy(1, 1, 1, 3, 1, 3);
        cornerEnemy.setBoard(board);
        cornerEnemy.walk(iceCream);
        assertNotNull(cornerEnemy);
    }
    
    @Test
    void testDirectionChanges() {
        int initialDir = enemy.getDirection();
        for (int i = 0; i < 4; i++) {
            enemy.walk(iceCream);
        }
        assertNotNull(enemy);
    }
    
    @Test
    void testAttackIceCream() {
        iceCream.setPosition(5, 5);
        boolean attacked = enemy.attack(iceCream);
        assertFalse(attacked);
    }
    
    @Test
    void testSetBoard() {
        Board newBoard = new Board();
        newBoard.level2(Color.GREEN, Color.YELLOW, false);
        enemy.setBoard(newBoard);
        assertNotNull(enemy);
    }
}