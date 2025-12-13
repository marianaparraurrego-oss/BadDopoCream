package domain;


import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;


public class FearfulAITest {


@Test
void fearfulAISeInicializa() {
FearfulAI ai = new FearfulAI(2, 2, Color.RED);
assertNotNull(ai);
}
}