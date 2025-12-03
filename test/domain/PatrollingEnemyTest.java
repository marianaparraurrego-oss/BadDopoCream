package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import org.junit.jupiter.api.*;
public class PatrollingEnemyTest {
	private PatrollingEnemy enemy;
	private Board board;
	private IceCream iceCream;
	
	@BeforeEach
	public void setUp() {
		board = new Board();
		board.level1(Color.WHITE);
		enemy = new PatrollingEnemy(5,5,2,10,2,10);
		enemy.setBoard(board);
		iceCream = new IceCream(15,15,Color.WHITE);
		iceCream.setBoard(board);
	}
	@Test
	public void testEnemyCreation() {
		assertEquals(5, enemy.getGridX());
		assertEquals(5, enemy.getGridY());
		assertNotNull(enemy.getColor());
	}
	
	@Test 
	public void testGetType() {
		assertEquals("Troll", enemy.getType());
	}
	
	@Test
	public void testWalk() {
		int originalX = enemy.getGridX();
		int originalY = enemy.getGridY();
		for(int i = 0; i < 5; i++) {
			enemy.walk(iceCream);
		}
		
		boolean moved = (enemy.getGridX() != originalX) || (enemy.getGridY() != originalY);
		assertTrue(moved);
	}
	
	@Test
	public void testAttack() {
		IceCream testIce= new IceCream(5,6, Color.WHITE);
		testIce.setBoard(board);
		
		assertTrue(enemy.canAttack());
		assertEquals(1, enemy.getAttackRange());
	}

	
	@Test
	public void testStaysWithinBounds() {
		for(int i = 0; i < 100; i++) {
			enemy.walk(iceCream);
			assertTrue(enemy.getGridX() >=2 && enemy.getGridX() <= 10);
			assertTrue(enemy.getGridY() >=2 && enemy.getGridY() <= 10);

		}
	}
}
