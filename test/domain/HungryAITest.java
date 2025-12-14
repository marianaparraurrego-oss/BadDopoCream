package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import org.junit.jupiter.api.Test;

public class HungryAITest {

    @Test
    void hungryAISeInicializaCorrectamente() {
        HungryAI ai = new HungryAI(2, 3, Color.ORANGE);

        assertEquals(2, ai.getGridX());
        assertEquals(3, ai.getGridY());
        assertEquals("hungry", ai.getProfile());
    }

    @Test
    void makeDecision_sinBoard_noFalla() {
        HungryAI ai = new HungryAI(1, 1, Color.BLUE);

        assertDoesNotThrow(ai::makeDecision);
    }

    @Test
    void makeDecision_conBoardSinFrutas_noLanzaExcepcion() {
        HungryAI ai = new HungryAI(1, 1, Color.GREEN);
        Board board = new Board(); // board real

        ai.setBoard(board);

        assertDoesNotThrow(ai::makeDecision);
    }
}
