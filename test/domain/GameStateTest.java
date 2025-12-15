package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class GameStateTest {
    
    private GameState gameState;
    private GameController controller;
    private LevelConfiguration[] configs;
    
    @BeforeEach
    void setUp() {
        gameState = new GameState();
        configs = new LevelConfiguration[3];
        configs[0] = new LevelConfiguration("Banana", "Cherry", "Troll", "Bonfire");
        configs[1] = new LevelConfiguration("Grapes", "Cactus", "Pot", "HotTile");
        configs[2] = new LevelConfiguration("Pineapple", "Cherry", "Narval", "Both");
        
        controller = new GameController(1, "Player", Color.BLUE, Color.RED, configs);
    }
    
    @Test
    void testConstructor() {
        assertNotNull(gameState.getFruits());
        assertNotNull(gameState.getEnemies());
        assertNotNull(gameState.getBlocks());
        assertNotNull(gameState.getBonfires());
        assertNotNull(gameState.getHotTiles());
    }
    
    @Test
    void testSetAndGetLevel() {
        gameState.setLevel(2);
        assertEquals(2, gameState.getLevel());
    }
    
    @Test
    void testSetAndGetGameMode() {
        gameState.setGameMode("PvP");
        assertEquals("PvP", gameState.getGameMode());
    }
    
    @Test
    void testSetAndGetScore() {
        gameState.setScore(1000);
        assertEquals(1000, gameState.getScore());
    }
    
    @Test
    void testSetAndGetScorePlayer1() {
        gameState.setScorePlayer1(500);
        assertEquals(500, gameState.getScorePlayer1());
    }
    
    @Test
    void testSetAndGetScorePlayer2() {
        gameState.setScorePlayer2(300);
        assertEquals(300, gameState.getScorePlayer2());
    }
    
    @Test
    void testSetAndGetFruitsCollected() {
        gameState.setFruitsCollected(10);
        assertEquals(10, gameState.getFruitsCollected());
    }
    
    @Test
    void testSetAndGetElapsedTime() {
        gameState.setElapsedTime(5000L);
        assertEquals(5000L, gameState.getElapsedTime());
    }
    
    @Test
    void testSetAndGetIceCreamColor() {
        gameState.setIceCreamColor(Color.BLUE);
        assertEquals(Color.BLUE, gameState.getIceCreamColor());
    }
    
    @Test
    void testSetAndGetIceCreamColor2() {
        gameState.setIceCreamColor2(Color.RED);
        assertEquals(Color.RED, gameState.getIceCreamColor2());
    }
    
    @Test
    void testSetAndGetIceCream1Positions() {
        gameState.setIceCream1X(10);
        gameState.setIceCream1Y(15);
        assertEquals(10, gameState.getIceCream1X());
        assertEquals(15, gameState.getIceCream1Y());
    }
    
    @Test
    void testSetAndGetIceCream2Positions() {
        gameState.setIceCream2X(12);
        gameState.setIceCream2Y(18);
        assertEquals(12, gameState.getIceCream2X());
        assertEquals(18, gameState.getIceCream2Y());
    }
    
    @Test
    void testSetAndGetCurrentWave() {
        gameState.setCurrentWave(1);
        assertEquals(1, gameState.getCurrentWave());
    }
    
    @Test
    void testFromGameController() {
        GameState state = GameState.fromGameController(controller);
        assertNotNull(state);
        assertEquals(1, state.getLevel());
        assertEquals("Player", state.getGameMode());
        assertEquals(Color.BLUE, state.getIceCreamColor());
        assertEquals(Color.RED, state.getIceCreamColor2());
    }
    
    @Test
    void testFromGameControllerCopiesCollections() {
        GameState state = GameState.fromGameController(controller);
        assertNotNull(state.getFruits());
        assertNotNull(state.getEnemies());
        assertNotNull(state.getBlocks());
        assertNotNull(state.getBonfires());
        assertNotNull(state.getHotTiles());
    }
    
    @Test
    void testGetGrid() {
        assertNull(gameState.getGrid());
        GameState state = GameState.fromGameController(controller);
        assertNotNull(state.getGrid());
        assertEquals(16, state.getGrid().length);
    }
    
    @Test
    void testSetAndGetLevelConfigs() {
        gameState.setLevelConfigs(configs);
        assertArrayEquals(configs, gameState.getLevelConfigs());
    }
    
    @Test
    void testFromGameControllerPreservesPositions() {
        GameState state = GameState.fromGameController(controller);
        IceCream ice1 = controller.getBoard().getIceCream();
        assertEquals(ice1.getGridX(), state.getIceCream1X());
        assertEquals(ice1.getGridY(), state.getIceCream1Y());
    }
}