package domain;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class IceCreamAITest {

    private static class DummyAI extends IceCreamAI {
        public DummyAI() {
            super(1, 1, Color.BLUE, "Dummy");
        }

        @Override
        public void makeDecision() {
            // vacío a propósito
        }
    }

    @Test
    void iceCreamAISeInicializa() {
        IceCreamAI ai = new DummyAI();
        assertEquals("Dummy", ai.getProfile());
    }

    @Test
    void setGameMode_funciona() {
        IceCreamAI ai = new DummyAI();
        ai.setGameMode("PvM");

        assertDoesNotThrow(() -> ai.makeDecision());
    }

    @Test
    void attemptBreakIce_sinBoard_noFalla() {
        IceCreamAI ai = new DummyAI();
        assertDoesNotThrow(ai::attemptBreakIce);
    }

    @Test
    void isTrapped_sinBoard_retornaFalse() {
        IceCreamAI ai = new DummyAI();
        assertFalse(ai.isTrapped());
    }
}
