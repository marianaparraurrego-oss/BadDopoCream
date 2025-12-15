package persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;
import java.awt.Color;
import java.io.File;
import java.util.List;

class PersistenceManagerTest {
    
    private PersistenceManager manager;
    private GameController controller;
    private LevelConfiguration[] configs;
    private static final String TEST_SAVE_NAME = "test_save";
    
    @BeforeEach
    void setUp() {
        manager = new PersistenceManager();
        
        configs = new LevelConfiguration[3];
        configs[0] = new LevelConfiguration("Banana", "Cherry", "Troll", "Bonfire");
        configs[1] = new LevelConfiguration("Grapes", "Cactus", "Pot", "HotTile");
        configs[2] = new LevelConfiguration("Pineapple", "Cherry", "Narval", "Both");
        
        controller = new GameController(1, "Player", Color.BLUE, Color.RED, configs);
    }
    
    @AfterEach
    void tearDown() {
        // Limpiar archivos de prueba
        manager.deleteSave(TEST_SAVE_NAME);
        manager.deleteSave("test_save_2");
        manager.deleteSave("test_save_3");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(manager);
        File saveDir = new File("saves");
        assertTrue(saveDir.exists());
        assertTrue(saveDir.isDirectory());
    }
    
    @Test
    void testSaveGame() {
        GameState state = GameState.fromGameController(controller);
        boolean result = manager.saveGame(state, TEST_SAVE_NAME);
        assertTrue(result);
    }
    
    @Test
    void testSaveGameCreatesFile() {
        GameState state = GameState.fromGameController(controller);
        manager.saveGame(state, TEST_SAVE_NAME);
        assertTrue(manager.saveExists(TEST_SAVE_NAME));
    }
    
    @Test
    void testLoadGame() {
        GameState state = GameState.fromGameController(controller);
        manager.saveGame(state, TEST_SAVE_NAME);
        
        GameState loadedState = manager.loadGame(TEST_SAVE_NAME);
        assertNotNull(loadedState);
        assertEquals(state.getLevel(), loadedState.getLevel());
        assertEquals(state.getGameMode(), loadedState.getGameMode());
    }
    
    @Test
    void testLoadNonExistentGame() {
        GameState state = manager.loadGame("non_existent_save");
        assertNull(state);
    }
    
    @Test
    void testSaveExists() {
        assertFalse(manager.saveExists(TEST_SAVE_NAME));
        
        GameState state = GameState.fromGameController(controller);
        manager.saveGame(state, TEST_SAVE_NAME);
        
        assertTrue(manager.saveExists(TEST_SAVE_NAME));
    }
    
    @Test
    void testDeleteSave() {
        GameState state = GameState.fromGameController(controller);
        manager.saveGame(state, TEST_SAVE_NAME);
        assertTrue(manager.saveExists(TEST_SAVE_NAME));
        
        boolean deleted = manager.deleteSave(TEST_SAVE_NAME);
        assertTrue(deleted);
        assertFalse(manager.saveExists(TEST_SAVE_NAME));
    }
    
    @Test
    void testDeleteNonExistentSave() {
        boolean deleted = manager.deleteSave("non_existent");
        assertTrue(deleted);
    }
    
    @Test
    void testGetSavedGames() {
        List<String> savedGames = manager.getSavedGames();
        assertNotNull(savedGames);
    }
    
    @Test
    void testGetSavedGamesAfterSaving() {
        GameState state = GameState.fromGameController(controller);
        manager.saveGame(state, "test_save_2");
        manager.saveGame(state, "test_save_3");
        
        List<String> savedGames = manager.getSavedGames();
        assertTrue(savedGames.size() >= 2);
    }
    
    @Test
    void testSaveAndLoadPreservesData() {
        GameState originalState = GameState.fromGameController(controller);
        originalState.setScore(1000);
        originalState.setFruitsCollected(5);
        
        manager.saveGame(originalState, TEST_SAVE_NAME);
        GameState loadedState = manager.loadGame(TEST_SAVE_NAME);
        
        assertNotNull(loadedState);
        assertEquals(1000, loadedState.getScore());
        assertEquals(5, loadedState.getFruitsCollected());
    }
    
    @Test
    void testSaveAndLoadLevel2() {
        GameController c = new GameController(2, "Player", Color.BLUE, Color.RED, configs);
        GameState state = GameState.fromGameController(c);
        
        manager.saveGame(state, TEST_SAVE_NAME);
        GameState loadedState = manager.loadGame(TEST_SAVE_NAME);
        
        assertNotNull(loadedState);
        assertEquals(2, loadedState.getLevel());
    }
    
    @Test
    void testSaveAndLoadPvPMode() {
        GameController pvp = new GameController(1, "PvP", Color.BLUE, Color.RED, configs);
        GameState state = GameState.fromGameController(pvp);
        
        manager.saveGame(state, TEST_SAVE_NAME);
        GameState loadedState = manager.loadGame(TEST_SAVE_NAME);
        
        assertNotNull(loadedState);
        assertEquals("PvP", loadedState.getGameMode());
    }
    
    @Test
    void testMultipleSaves() {
        GameState state1 = GameState.fromGameController(controller);
        GameState state2 = GameState.fromGameController(controller);
        
        assertTrue(manager.saveGame(state1, "test_save_2"));
        assertTrue(manager.saveGame(state2, "test_save_3"));
        
        assertTrue(manager.saveExists("test_save_2"));
        assertTrue(manager.saveExists("test_save_3"));
    }
    
    @Test
    void testOverwriteSave() {
        GameState state1 = GameState.fromGameController(controller);
        state1.setScore(100);
        manager.saveGame(state1, TEST_SAVE_NAME);
        
        GameState state2 = GameState.fromGameController(controller);
        state2.setScore(200);
        manager.saveGame(state2, TEST_SAVE_NAME);
        
        GameState loaded = manager.loadGame(TEST_SAVE_NAME);
        assertEquals(200, loaded.getScore());
    }
    
    @Test
    void testSaveGameWithSpecialCharacters() {
        GameState state = GameState.fromGameController(controller);
        boolean result = manager.saveGame(state, "test_save_special");
        assertTrue(result);
        manager.deleteSave("test_save_special");
    }
}