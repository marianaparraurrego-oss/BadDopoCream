package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

public class BananaTest {
	private Banana banana;
	@BeforeEach
	public void setUp() {
		banana = new Banana(3,7);
	}
	
	@Test
	public void testBananaCreation() {
		assertEquals(3,banana.getGridX());
		assertEquals(7,banana.getGridY());
		assertEquals(Color.YELLOW, banana.getColor());
		assertTrue(banana.isVisibleFruit());
	}
	
	@Test
	public void testGetType() {
		assertEquals("Banana", banana.getType());
	}
	
	@Test
	public void testMove() {
		int originalX = banana.getGridX();
		int originalY = banana.getGridY();
		banana.move();
		assertEquals(originalX, banana.getGridX());
		assertEquals(originalY, banana.getGridY());
	}
	
	@Test
	public void testVisibility() {
		banana.makeInvisible();
		assertFalse(banana.isVisibleFruit());
		banana.makeVisible();
		assertTrue(banana.isVisibleFruit());
	}
	@Test
    void testPoints() {
        assertEquals(100, banana.getPoints());
    }
	@Test
    void testMakeVisible() {
        banana.makeInvisible();
        banana.makeVisible();
        assertTrue(banana.isVisibleFruit());
    }
	

}
