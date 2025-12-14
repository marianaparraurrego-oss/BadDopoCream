package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;


public class IceCreamTest {
    private IceCream iceCream;
    private Board board;

    @BeforeEach
    public void setUp(){
        board = new Board();
        board.level1(Color.WHITE, null, false);
        iceCream = new IceCream(5, 5, Color.WHITE);
        iceCream.setBoard(board);
    }

    @Test
    public void testIceCreamCreation(){
        assertEquals(5, iceCream.getGridX());
        assertEquals(5, iceCream.getGridY());
        assertEquals(Color.WHITE, iceCream.getColor());
        assertEquals(2, iceCream.getDirection());
    }

    @Test
    public void testMove(){
        int originalX = iceCream.getGridX();
        int originalY = iceCream.getGridY();
        iceCream.move(2);
        assertEquals(originalX, iceCream.getGridX());
    }

    @Test
    public void testCannotMoveThroughWalls(){
        IceCream testIce = new IceCream(1, 1, Color.WHITE);
        testIce.setBoard(board);
        testIce.move(3);
        assertEquals(1, testIce.getGridX());
    }

    @Test
    public void testShootIce(){
        Block newBlock = iceCream.shootIce();
        assertNotNull(newBlock);
    }

    @Test
    public void testBreakBlock() {
        board.setBlock(5, 6);
        assertTrue(board.hasBlock(5, 6));
        iceCream.breakBlock(2);
        assertFalse(board.hasBlock(5, 6));
    }    

    @Test
    public void testCanBreakBlocks(){
        assertTrue(iceCream.canBreakBlocks());
    }

    @Test
    public void testGetBreakBlocksLine(){
        assertTrue(iceCream.getBreakBlocksLine() > 0);
    }

    @Test
    public void testDie(){
        iceCream.die();
        assertEquals(-1, iceCream.getGridX());
        assertEquals(-1, iceCream.getGridY());
    }
    @Test
    void setPosition_actualizaCoordenadas() {
        IceCream ice = new IceCream(0, 0, Color.PINK);

        ice.setPosition(5, 6);

        assertEquals(5, ice.getGridX());
        assertEquals(6, ice.getGridY());
    }
}