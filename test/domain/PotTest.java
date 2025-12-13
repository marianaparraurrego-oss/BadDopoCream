package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PotTest {

	@Test
	void potSeCreaCorrectamente() {
		Pot pot = new Pot(3, 3);
		assertEquals(3, pot.getGridX());
		assertEquals(3, pot.getGridY());
	}
}