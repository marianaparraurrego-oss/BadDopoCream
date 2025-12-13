package domain;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class ExpertAITest {

    @Test
    void expertAISeInicializaCorrectamente() {
        ExpertAI ai = new ExpertAI(1, 1, Color.BLUE);
        assertNotNull(ai);
    }

    @Test
    void setBoard_noLanzaExcepcion() {
        ExpertAI ai = new ExpertAI(1, 1, Color.BLUE);
        Board board = new Board();

        assertDoesNotThrow(() -> ai.setBoard(board));
    }
}
