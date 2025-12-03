package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

public class BlockTest {
	private Block block;

	@BeforeEach
	public void setUp () {
		block = new Block(5,10);
	}
	@Test
	public void testBlockCreation() {
		assertEquals(5, block.getGridX());
		assertEquals(10, block.getGridY());
		assertTrue(block.isVisible());
	}
	
	public void testMakeVisible() {
		block.makeInvisible();
		assertFalse(block.isVisible());
		block.makeVisible();
		assertTrue(block.isVisible());
	}
	
	@Test
	public void testMakeInvisible() {
		block.makeInvisible();
		assertFalse(block.isVisible());
	}
	
	@Test
	public void testBreakBlock() {
		block.breakBlock();
		assertFalse(block.isVisible());
	}
	
	@Test
	public void testCreate() {
		block.makeInvisible();
		block.create();
		assertTrue(block.isVisible());
	}

}
