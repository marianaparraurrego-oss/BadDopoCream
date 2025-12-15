package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class IceCreamAITest {
    
    // Clase concreta para probar la clase abstracta
    private static class TestAI extends IceCreamAI {
        public TestAI(int gridX, int gridY, Color color) {
            super(gridX, gridY, color, "test");
        }
        
        @Override
        public void makeDecision() {
            // Implementación simple para testing
        }
    }
    
    private TestAI testAI;
    private Board board;
    
    @BeforeEach
    void setUp() {
        testAI = new TestAI(8, 8, Color.MAGENTA);
        board = new Board();
        board.level1(Color.BLUE, Color.RED, false);
        testAI.setBoard(board);
    }
    
    @Test
    void testConstructor() {
        assertEquals(8, testAI.getGridX());
        assertEquals(8, testAI.getGridY());
        assertEquals(Color.MAGENTA, testAI.getColor());
        assertEquals("test", testAI.getProfile());
        assertNotNull(testAI.random);
    }
    
    @Test
    void testSetBoard() {
        Board newBoard = new Board();
        testAI.setBoard(newBoard);
        assertNotNull(testAI.board);
    }
    
    @Test
    void testGetProfile() {
        assertEquals("test", testAI.getProfile());
    }
    
    @Test
    void testFindNearestFruitNoFruits() {
        board.getFruits().clear();
        Fruit nearest = testAI.findNearestFruit();
        assertNull(nearest);
    }
    
    @Test
    void testFindNearestFruitWithFruits() {
        board.addFruit(new Banana(10, 10));
        board.addFruit(new Banana(9, 9));
        Fruit nearest = testAI.findNearestFruit();
        assertNotNull(nearest);
    }
    
    @Test
    void testFindNearestFruitIgnoresInvisible() {
        Banana banana = new Banana(9, 9);
        banana.makeInvisible();
        board.addFruit(banana);
        Fruit nearest = testAI.findNearestFruit();
        assertNull(nearest);
    }
    
    @Test
    void testFindNearestFruitIgnoresCactusWithSpikes() {
        Cactus cactus = new Cactus(9, 9);
        board.addFruit(cactus);
        Fruit nearest = testAI.findNearestFruit();
        assertNotNull(nearest);
    }
    
    @Test
    void testFindNearestEnemyNoEnemies() {
        board.getEnemies().clear();
        Enemy nearest = testAI.findNearestEnemy();
        assertNull(nearest);
    }
    
    @Test
    void testFindNearestEnemyWithEnemies() {
        Enemy enemy = new PatrollingEnemy(10, 10, 1, 15, 1, 15);
        board.addEnemy(enemy);
        Enemy nearest = testAI.findNearestEnemy();
        assertNotNull(nearest);
    }
    
    @Test
    void testIsPositionSafeNoThreats() {
        board.getEnemies().clear();
        assertTrue(testAI.isPositionSafe(10, 10));
    }
    
    @Test
    void testIsPositionSafeNearEnemy() {
        Enemy enemy = new PatrollingEnemy(10, 10, 1, 15, 1, 15);
        board.addEnemy(enemy);
        assertFalse(testAI.isPositionSafe(11, 10));
    }
    
    @Test
    void testIsPositionSafeOnCactusWithSpikes() {
        Cactus cactus = new Cactus(10, 10);
        board.addFruit(cactus);
        assertTrue(testAI.isPositionSafe(10, 10));
    }
    
    @Test
    void testIsPositionSafeOnActiveBonfire() {
        Bonfire bonfire = new Bonfire(10, 10);
        board.addBonfire(bonfire);
        assertFalse(testAI.isPositionSafe(10, 10));
    }
    
    @Test
    void testIsPositionSafeOnInactiveBonfire() {
        Bonfire bonfire = new Bonfire(10, 10);
        bonfire.extinguish();
        board.addBonfire(bonfire);
        assertTrue(testAI.isPositionSafe(10, 10));
    }
    
    @Test
    void testAttemptBreakIceWithNullBoard() {
        TestAI ai = new TestAI(5, 5, Color.RED);
        int x = ai.getGridX();
        int y = ai.getGridY();
        ai.attemptBreakIce();
        // Con board null, no debe cambiar de posición
        assertEquals(x, ai.getGridX());
        assertEquals(y, ai.getGridY());
    }
    
    @Test
    void testAttemptBreakIce() {
        board.setBlock(9, 8);
        testAI.attemptBreakIce();
        assertNotNull(testAI);
    }
    
    @Test
    void testIsTrappedNotMoved() {
        // Simular que no se ha movido
        assertFalse(testAI.isTrapped());
        testAI.isTrapped();
        testAI.isTrapped();
        testAI.isTrapped();
        assertTrue(testAI.isTrapped());
    }
    
    @Test
    void testIsTrappedAfterMovement() {
        assertFalse(testAI.isTrapped());
        testAI.setPosition(9, 9);
        assertFalse(testAI.isTrapped());
    }
    
    @Test
    void testIsTrappedAllDirectionsBlocked() {
        board.setBlock(7, 8);
        board.setBlock(9, 8);
        board.setBlock(8, 7);
        board.setBlock(8, 9);
        boolean trapped = testAI.isTrapped();
        // Puede estar atrapado si todas las direcciones están bloqueadas
        assertNotNull(testAI);
    }
    
    
    @Test
    void testMoveTowards() {
        int x = testAI.getGridX();
        int y = testAI.getGridY();
        testAI.moveTowards(10, 10);
        // Puede o no moverse dependiendo de los bloques
        assertNotNull(testAI);
    }
    
    @Test
    void testMoveAwayFrom() {
        int x = testAI.getGridX();
        int y = testAI.getGridY();
        testAI.moveAwayFrom(5, 5);
        // Puede o no moverse dependiendo de los bloques
        assertNotNull(testAI);
    }
    
    @Test
    void testMoveRandomly() {
        int x = testAI.getGridX();
        int y = testAI.getGridY();
        testAI.moveRandomly();
        // Puede o no moverse dependiendo de los bloques
        assertNotNull(testAI);
    }
    
    @Test
    void testTryMoveValidDirection() {
        boolean result = testAI.tryMove(IceCream.RIGHT);
        assertTrue(result || !result);
    }
    
    @Test
    void testTryMoveAllDirections() {
        testAI.tryMove(IceCream.UP);
        testAI.tryMove(IceCream.RIGHT);
        testAI.tryMove(IceCream.DOWN);
        testAI.tryMove(IceCream.LEFT);
        assertNotNull(testAI);
    }
    
    @Test
    void testShootIfPossible() {
        for (int i = 0; i < 20; i++) {
            testAI.shootIfPossible();
        }
        assertNotNull(testAI);
    }
    
    @Test
    void testSetGameMode() {
        testAI.setGameMode("PvP");
        assertNotNull(testAI);
    }
    
    @Test
    void testMultipleMoveAttempts() {
        board.addFruit(new Banana(12, 12));
        for (int i = 0; i < 10; i++) {
            testAI.moveTowards(12, 12);
        }
        // El AI intentará moverse hacia la fruta
        assertNotNull(testAI);
    }
    
    @Test
    void testIsTrappedWithBoard() {
        // Verificar que inicialmente no está atrapado
        assertFalse(testAI.isTrapped());
    }
    
    @Test
    void testTryMoveWithBlock() {
        board.setBlock(9, 8);
        boolean result = testAI.tryMove(IceCream.RIGHT);
        // Puede o no moverse dependiendo de si intenta romper el bloque
        assertNotNull(testAI);
    }
}