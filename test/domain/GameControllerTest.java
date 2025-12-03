package domain;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameControllerTest {
    private GameController controller;

    @BeforeEach
    public void setUp(){
        controller = new GameController(1, "PvP", Color.WHITE, Color.PINK);
    }

    @Test
    public void testControllerCreation() {
        assertEquals(1, controller.getLevel());
        assertEquals("PvP", controller.getGameMode());
        assertTrue(controller.isGameRunning());
        assertFalse(controller.isGamePaused());
        assertEquals(0,controller.getFruitsCollected());
        assertEquals(16, controller.getTotalFruits());
    }

    @Test
    public void testPauseGame(){
        assertFalse(controller.isGamePaused());
        controller.pauseGame();
        assertTrue(controller.isGamePaused());
        controller.pauseGame();
        assertFalse(controller.isGamePaused());
    }

    @Test
    public void testMovePlayer(){
        Board board = controller.getBoard();
        IceCream iceCream = board.getIceCream();
        int originalX = iceCream.getGridX();
        int originalY = iceCream.getGridY();
        controller.movePlayer(1);
        boolean moved = (iceCream.getGridX() != originalX) || (iceCream.getGridY() != originalY);
        assertTrue(moved);
    }

    @Test
    public void shootIce(){
        Board board = controller.getBoard();
        int blocksBefore = board.getBlocks().size();
        controller.shootIce();
        assertTrue(controller.isGameRunning());
    }

    @Test
    public void testTimeRemaining(){
        int time = controller.getTimeRemaining();
        assertTrue(time > 0 && time <= 180);
    }

    @Test
    public void testResetLevel(){
        controller.resetLevel();
        assertEquals(0, controller.getFruitsCollected());
        assertTrue(controller.isGameRunning());
    }

    @Test
    public void testDidWin(){
        assertFalse(controller.didWin());
    }    

    @Test
    public void testGetBoard(){
        assertNotNull(controller.getBoard());
    }

}