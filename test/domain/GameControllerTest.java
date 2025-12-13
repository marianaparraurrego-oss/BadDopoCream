package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.BadDopoCreamExceptions;

/**
 * Pruebas unitarias de GameController
 * SIN Mockito – solo JUnit 5 y objetos reales
 */
public class GameControllerTest {

    private LevelConfiguration[] configs;

    @BeforeEach
    void setup() {
        configs = new LevelConfiguration[] {
            new LevelConfiguration("Troll", "Bonfire", "Grapes", "Banana"),
            new LevelConfiguration("Pot", "HotTile", "Cherry", "Grapes"),
            new LevelConfiguration("Narval", "Both", "Pineapple", "Cherry")
        };
    }

    @Test
    void constructorInicializaValoresBasicos() {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        assertEquals(1, gc.getLevel());
        assertEquals("Player", gc.getGameMode());
        assertEquals(0, gc.getScore());
        assertEquals(0, gc.getFruitsCollected());
        assertTrue(gc.isGameRunning());
        assertFalse(gc.isGamePaused());
        assertNotNull(gc.getBoard());
    }

    @Test
    void constructorMvM_creaDosIAs() {
        GameController gc =
                new GameController(1, "MvM", Color.BLUE, Color.RED, configs);

        assertNotNull(gc.getAIPlayer1());
        assertNotNull(gc.getAIPlayer2());
    }

    @Test
    void pauseGame_alternaEstado() {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        gc.pauseGame();
        assertTrue(gc.isGamePaused());

        gc.pauseGame();
        assertFalse(gc.isGamePaused());
    }

    @Test
    void movePlayer_noLanzaExcepcion() {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        assertDoesNotThrow(() -> gc.movePlayer(IceCream.RIGHT));
    }

    @Test
    void movePlayer2_noLanzaExcepcionEnCualquierModo() {
        GameController gc1 =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);
        GameController gc2 =
                new GameController(1, "PvP", Color.BLUE, Color.RED, configs);

        assertDoesNotThrow(() -> gc1.movePlayer2(IceCream.LEFT));
        assertDoesNotThrow(() -> gc2.movePlayer2(IceCream.LEFT));
    }

    @Test
    void resetLevel_reiniciaPeroMantieneNivel() {
        GameController gc =
                new GameController(2, "Player", Color.BLUE, Color.RED, configs);

        gc.resetLevel();

        assertEquals(2, gc.getLevel());
        assertEquals(0, gc.getScore());
        assertEquals(0, gc.getFruitsCollected());
        assertTrue(gc.isGameRunning());
    }

    @Test
    void resetToLevel1_fijaNivelUno() {
        GameController gc =
                new GameController(3, "Player", Color.BLUE, Color.RED, configs);

        gc.resetToLevel1();

        assertEquals(1, gc.getLevel());
        assertEquals(0, gc.getScore());
        assertTrue(gc.isGameRunning());
    }

    @Test
    void nextLevel_incrementaHastaMaximo() {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        gc.nextLevel();
        assertEquals(2, gc.getLevel());

        gc.nextLevel();
        assertEquals(3, gc.getLevel());

        gc.nextLevel();
        assertEquals(3, gc.getLevel()); // no pasa del último nivel
    }

    @Test
    void getTimeRemaining_disminuyeConElTiempo() throws InterruptedException {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        int t1 = gc.getTimeRemaining();
        Thread.sleep(1100);
        int t2 = gc.getTimeRemaining();

        assertTrue(t2 < t1);
    }

    @Test
    void didWin_esFalseInicialmente() {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        assertFalse(gc.didWin());
    }

    @Test
    void loadFromState_nullLanzaExcepcion() {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        assertThrows(
                BadDopoCreamExceptions.class,
                () -> gc.loadFromState(null)
        );
    }

    @Test
    void loadFromState_cargaDatosBasicos() throws Exception {
        GameController gc =
                new GameController(1, "Player", Color.BLUE, Color.RED, configs);

        GameState state = new GameState(); // constructor REAL (simple)

        state.setLevel(2);
        state.setGameMode("Player");
        state.setScore(50);
        state.setFruitsCollected(3);

        gc.loadFromState(state);

        assertEquals(2, gc.getLevel());
        assertEquals(50, gc.getScore());
        assertEquals(3, gc.getFruitsCollected());
        assertTrue(gc.isGameRunning());
    }
}
