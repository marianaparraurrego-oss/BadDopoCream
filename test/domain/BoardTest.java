package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class BoardTest {
    
    private Board board;
    
    @BeforeEach
    void setUp() {
        board = new Board();
    }
    
    @Test
    void testConstructor() {
        assertEquals(16, board.getRows());
        assertEquals(18, board.getCols());
        assertEquals(30, board.getCellSize());
        assertEquals(540, board.getWidth());
        assertEquals(480, board.getHeight());
        assertNotNull(board.getGrid());
        assertNotNull(board.getBlocks());
        assertNotNull(board.getEnemies());
        assertNotNull(board.getFruits());
        assertNotNull(board.getBonfires());
        assertNotNull(board.getHotTiles());
    }
    
    @Test
    void testLevel1() {
        board.level1(Color.BLUE, Color.RED, false);
        assertNotNull(board.getIceCream());
        assertNotNull(board.getIceCream2());
        assertTrue(board.getBlocks().size() > 0);
    }
    
    @Test
    void testLevel2() {
        board.level2(Color.BLUE, Color.RED, false);
        assertNotNull(board.getIceCream());
        assertNotNull(board.getIceCream2());
        assertTrue(board.getBlocks().size() > 0);
    }
    
    @Test
    void testLevel3() {
        board.level3(Color.BLUE, Color.RED, false);
        assertNotNull(board.getIceCream());
        assertNotNull(board.getIceCream2());
        assertTrue(board.getBlocks().size() > 0);
    }
    
    @Test
    void testCanMoveToValidPosition() {
        board.level1(Color.BLUE, Color.RED, false);
        assertTrue(board.canMoveTo(5, 5));
    }
    
    @Test
    void testCanMoveToInvalidPosition() {
        board.level1(Color.BLUE, Color.RED, false);
        assertFalse(board.canMoveTo(-1, 5));
        assertFalse(board.canMoveTo(5, -1));
        assertFalse(board.canMoveTo(100, 5));
        assertFalse(board.canMoveTo(5, 100));
    }
    
    @Test
    void testCanMoveToWall() {
        board.level1(Color.BLUE, Color.RED, false);
        assertFalse(board.canMoveTo(0, 5));
    }
    
    @Test
    void testHasBlockValid() {
        board.level1(Color.BLUE, Color.RED, false);
        assertTrue(board.hasBlock(0, 0));
    }
    
    @Test
    void testHasBlockInvalid() {
        board.level1(Color.BLUE, Color.RED, false);
        assertFalse(board.hasBlock(-1, -1));
        assertFalse(board.hasBlock(100, 100));
    }
    
    @Test
    void testSetBlock() {
        board.level1(Color.BLUE, Color.RED, false);
        int initialSize = board.getBlocks().size();
        board.setBlock(5, 5);
        assertTrue(board.hasBlock(5, 5));
    }
    
    @Test
    void testRemoveBlock() {
        board.level1(Color.BLUE, Color.RED, false);
        board.setBlock(5, 5);
        board.removeBlock(5, 5);
        assertFalse(board.hasBlock(5, 5));
    }
    
    @Test
    void testAddFruit() {
        board.level1(Color.BLUE, Color.RED, false);
        Fruit banana = new Banana(10, 10);
        board.addFruit(banana);
        assertTrue(board.getFruits().contains(banana));
    }
    
    @Test
    void testAddEnemy() {
        board.level1(Color.BLUE, Color.RED, false);
        Enemy enemy = new PatrollingEnemy(5, 5, 1, 10, 1, 10);
        board.addEnemy(enemy);
        assertTrue(board.getEnemies().contains(enemy));
    }
    
    @Test
    void testAddBonfire() {
        board.level1(Color.BLUE, Color.RED, false);
        Bonfire bonfire = new Bonfire(7, 7);
        board.addBonfire(bonfire);
        assertTrue(board.getBonfires().contains(bonfire));
    }
    
    @Test
    void testAddHotTile() {
        board.level1(Color.BLUE, Color.RED, false);
        HotTile hotTile = new HotTile(8, 8);
        board.addHotTile(hotTile);
        assertTrue(board.getHotTiles().contains(hotTile));
    }
    
    @Test
    void testIsSafeForFruit() {
        board.level1(Color.BLUE, Color.RED, false);
        assertTrue(board.isSafeForFruit(5, 5));
    }
    
    @Test
    void testIsSafeForFruitInvalidPositions() {
        board.level1(Color.BLUE, Color.RED, false);
        assertFalse(board.isSafeForFruit(0, 0));
        assertFalse(board.isSafeForFruit(17, 15));
    }
    
    @Test
    void testReplaceIceCream() {
        board.level1(Color.BLUE, Color.RED, false);
        IceCream newIce = new IceCream(10, 10, Color.GREEN);
        board.replaceIceCream(newIce);
        assertEquals(newIce, board.getIceCream());
    }
    
    @Test
    void testReplaceIceCream2() {
        board.level1(Color.BLUE, Color.RED, false);
        IceCream newIce = new IceCream(12, 12, Color.YELLOW);
        board.replaceIceCream2(newIce);
        assertEquals(newIce, board.getIceCream2());
    }
    
    @Test
    void testGetColor() {
        assertNotNull(board.getColor());
    }
    
    @Test
    void testSetBlockOnHotTile() {
        board.level1(Color.BLUE, Color.RED, false);
        HotTile hotTile = new HotTile(7, 7);
        board.addHotTile(hotTile);
        board.setBlock(7, 7);
        assertFalse(board.hasBlock(7, 7));
    }
    
    @Test
    void testSetBlockExtinguishesBonfire() {
        board.level1(Color.BLUE, Color.RED, false);
        Bonfire bonfire = new Bonfire(6, 6);
        board.addBonfire(bonfire);
        board.setBlock(6, 6);
        assertFalse(bonfire.isActive());
    }
}