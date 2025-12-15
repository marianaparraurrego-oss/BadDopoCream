package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import exceptions.BadDopoCreamExceptions;

class GameControllerTest {
    
    private GameController controller;
    private LevelConfiguration[] configs;
    
    @BeforeEach
    void setUp() {
        configs = new LevelConfiguration[3];
        configs[0] = new LevelConfiguration("Banana", "Cherry", "Troll", "Bonfire");
        configs[1] = new LevelConfiguration("Grapes", "Cactus", "Pot", "HotTile");
        configs[2] = new LevelConfiguration("Pineapple", "Cherry", "Narval", "Both");
        
        controller = new GameController(1, "Player", Color.BLUE, Color.RED, configs);
    }
    
    @Test
    void testConstructorPlayerMode() {
        assertNotNull(controller.getBoard());
        assertEquals(1, controller.getLevel());
        assertTrue(controller.isGameRunning());
        assertFalse(controller.isGamePaused());
        assertEquals(0, controller.getScore());
        assertEquals(0, controller.getFruitsCollected());
    }
    
    @Test
    void testConstructorPvPMode() {
        GameController pvp = new GameController(1, "PvP", Color.BLUE, Color.RED, configs);
        assertEquals("PvP", pvp.getGameMode());
        assertNotNull(pvp.getBoard().getIceCream2());
    }
    
    @Test
    void testConstructorPvMMode() {
        GameController pvm = new GameController(1, "PvM", Color.BLUE, Color.RED, configs, "expert", "hungry");
        assertEquals("PvM", pvm.getGameMode());
        assertNull(pvm.getAIPlayer1());
        assertNotNull(pvm.getAIPlayer2());
    }
    
    @Test
    void testConstructorMvMMode() {
        GameController mvm = new GameController(1, "MvM", Color.BLUE, Color.RED, configs, "expert", "fearful");
        assertEquals("MvM", mvm.getGameMode());
        assertNotNull(mvm.getAIPlayer1());
        assertNotNull(mvm.getAIPlayer2());
    }
    
    @Test
    void testMovePlayer() {
        int initialX = controller.getBoard().getIceCream().getGridX();
        controller.movePlayer(IceCream.RIGHT);
        assertNotNull(controller.getBoard().getIceCream());
    }
    
    @Test
    void testMovePlayer2() {
        GameController pvp = new GameController(1, "PvP", Color.BLUE, Color.RED, configs);
        pvp.movePlayer2(IceCream.LEFT);
        assertNotNull(pvp.getBoard().getIceCream2());
    }
    
    @Test
    void testShootIce() {
        controller.shootIce();
        assertNotNull(controller.getBoard().getBlocks());
    }
    
    @Test
    void testShootIce2() {
        GameController pvp = new GameController(1, "PvP", Color.BLUE, Color.RED, configs);
        pvp.shootIce2();
        assertNotNull(pvp.getBoard().getBlocks());
    }
    
    @Test
    void testBreakIce() {
        controller.getBoard().setBlock(8, 11);
        controller.breakIce();
        assertNotNull(controller.getBoard());
    }
    
    @Test
    void testUpdate() {
        controller.update();
        assertTrue(controller.isGameRunning());
    }
    
    @Test
    void testPauseGame() {
        controller.pauseGame();
        assertTrue(controller.isGamePaused());
        controller.pauseGame();
        assertFalse(controller.isGamePaused());
    }
    
    @Test
    void testResetLevel() {
        controller.resetLevel();
        assertEquals(0, controller.getScore());
        assertEquals(0, controller.getFruitsCollected());
        assertTrue(controller.isGameRunning());
    }
    
    @Test
    void testResetToLevel1() {
        controller.resetToLevel1();
        assertEquals(1, controller.getLevel());
        assertEquals(0, controller.getScore());
        assertEquals(0, controller.getFruitsCollected());
    }
    
    @Test
    void testNextLevel() {
        controller.nextLevel();
        assertEquals(2, controller.getLevel());
    }
    
    @Test
    void testNextLevelAtMaxLevel() {
        GameController c = new GameController(3, "Player", Color.BLUE, Color.RED, configs);
        c.nextLevel();
        assertEquals(3, c.getLevel());
    }
    
    @Test
    void testGetTimeRemaining() {
        int time = controller.getTimeRemaining();
        assertTrue(time > 0 && time <= 180);
    }
    
    @Test
    void testDidWin() {
        assertFalse(controller.didWin());
    }
    
    @Test
    void testGetTotalFruits() {
        assertTrue(controller.getTotalFruits() > 0);
    }
    
    @Test
    void testGetScorePlayers() {
        GameController pvp = new GameController(1, "PvP", Color.BLUE, Color.RED, configs);
        assertEquals(0, pvp.getScorePlayer1());
        assertEquals(0, pvp.getScorePlayer2());
    }
    
    @Test
    void testSetEnemyMoveDelay() {
        controller.setEnemyMoveDelay(10);
        assertNotNull(controller);
    }
    
    @Test
    void testSetAIProfiles() {
        GameController mvm = new GameController(1, "MvM", Color.BLUE, Color.RED, configs, "hungry", "fearful");
        mvm.setAIProfiles("expert", "hungry");
        assertEquals("expert", mvm.getAI1Profile());
        assertEquals("hungry", mvm.getAI2Profile());
    }
    
    @Test
    void testGetAI1Profile() {
        GameController mvm = new GameController(1, "MvM", Color.BLUE, Color.RED, configs, "expert", "fearful");
        assertEquals("expert", mvm.getAI1Profile());
    }
    
    @Test
    void testGetAI2Profile() {
        GameController mvm = new GameController(1, "MvM", Color.BLUE, Color.RED, configs, "expert", "fearful");
        assertEquals("fearful", mvm.getAI2Profile());
    }
    
    @Test
    void testLoadFromStateNull() {
        assertThrows(BadDopoCreamExceptions.class, () -> {
            controller.loadFromState(null);
        });
    }
    
    @Test
    void testLoadFromStateValid() throws BadDopoCreamExceptions {
        GameState state = GameState.fromGameController(controller);
        controller.loadFromState(state);
        assertEquals(state.getLevel(), controller.getLevel());
    }
    
    @Test
    void testGetCurrentWave() {
        assertTrue(controller.getCurrentWave() >= 0);
    }
    
    @Test
    void testGetStartTime() {
        assertTrue(controller.getStartTime() > 0);
    }
    
    @Test
    void testGetLevelConfigs() {
        assertArrayEquals(configs, controller.getLevelConfigs());
    }
    
    @Test
    void testMultipleUpdates() {
        for (int i = 0; i < 10; i++) {
            controller.update();
        }
        assertTrue(controller.isGameRunning());
    }
    
    @Test
    void testUpdateWhilePaused() {
        controller.pauseGame();
        controller.update();
        assertTrue(controller.isGamePaused());
    }
    
    @Test
    void testLevel2Setup() {
        GameController c = new GameController(2, "Player", Color.BLUE, Color.RED, configs);
        assertEquals(2, c.getLevel());
        assertNotNull(c.getBoard());
    }
    
    @Test
    void testLevel3Setup() {
        GameController c = new GameController(3, "Player", Color.BLUE, Color.RED, configs);
        assertEquals(3, c.getLevel());
        assertNotNull(c.getBoard());
    }
    
    @Test
    void testConstructorWithNullAIProfiles() {
        GameController mvm = new GameController(1, "MvM", Color.BLUE, Color.RED, configs, null, null);
        assertEquals("expert", mvm.getAI1Profile());
        assertEquals("expert", mvm.getAI2Profile());
    }
    
    @Test
    void testConstructorWithEmptyAIProfiles() {
        GameController mvm = new GameController(1, "MvM", Color.BLUE, Color.RED, configs, "", "");
        assertEquals("expert", mvm.getAI1Profile());
        assertEquals("expert", mvm.getAI2Profile());
    }
}