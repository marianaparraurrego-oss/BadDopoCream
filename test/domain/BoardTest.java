package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.awt.Color;


public class BoardTest {
	private Board board;
	
	@BeforeEach
	public void setUp() {
		board= new Board();
		board.level1(Color.WHITE);
	}
	
	@Test
	public void testBoardCreation() {
		assertEquals(16, board.getRows());
		assertEquals(18, board.getCols());
		assertEquals(30, board.getCellSize());
		assertEquals(540, board.getWidth());
		assertEquals(480, board.getHeight());
	}
	
	@Test
	public void testLvel1Creation() {
		assertNotNull(board.getIceCream());
		assertFalse(board.getBlocks().isEmpty());
		assertFalse(board.getEnemies().isEmpty());
		assertFalse(board.getFruits().isEmpty());

		assertEquals(2, board.getEnemies().size());
		assertEquals(16, board.getFruits().size());

	}
	
	@Test
	public void testCanMoveTo() {
		assertTrue(board.canMoveTo(9, 9));
		
		assertFalse(board.canMoveTo(0, 0));
		assertFalse(board.canMoveTo(17, 0));
		assertFalse(board.canMoveTo(0, 15));
		assertFalse(board.canMoveTo(17, 15));
	}
	
	@Test
	public void testHasBlock() {
		assertTrue(board.hasBlock(0, 1));
		
		assertFalse(board.hasBlock(9, 9));
	}
	
	@Test
	public void testSetBlock() {
		int x = 10;
		int y = 10;
		
		assertFalse(board.hasBlock(x, y));
		board.setBlock(x, y);
		assertTrue(board.hasBlock(x, y));
	}
	
	@Test
	public void testBoundaryChecks() {
		assertFalse(board.canMoveTo(-1, 5));
		assertFalse(board.canMoveTo(5, -1));
		assertFalse(board.canMoveTo(20, 5));
		assertFalse(board.canMoveTo(5, 20));

	}

}
