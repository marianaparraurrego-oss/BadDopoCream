package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LevelConfigurationTest {

	@Test
	void gettersFuncionanCorrectamente() {
		LevelConfiguration config = new LevelConfiguration("Grapes", "Banana", "Troll", "Bonfire");

		assertEquals("Troll", config.getEnemyType());
		assertEquals("Bonfire", config.getObstacleType());
		assertEquals("Grapes", config.getFruit1Type());
		assertEquals("Banana", config.getFruit2Type());
	}
}
