package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    
    private Block block;
    
    @BeforeEach
    void setUp() {
        block = new Block(5, 10);
    }
    
    @Test
    void testBlockCreation() {
        assertEquals(5, block.getGridX());
        assertEquals(10, block.getGridY());
        assertTrue(block.isVisible());
    }
    
    @Test
    void testCreate() {
        block.makeInvisible();
        block.create();
        assertTrue(block.isVisible());
    }
    
    @Test
    void testBreakBlock() {
        block.breakBlock();
        assertFalse(block.isVisible());
    }
    
    @Test
    void testMakeVisible() {
        block.makeInvisible();
        assertFalse(block.isVisible());
        block.makeVisible();
        assertTrue(block.isVisible());
    }
    
    @Test
    void testMakeInvisible() {
        block.makeInvisible();
        assertFalse(block.isVisible());
    }
    
    @Test
    void testMultipleVisibilityChanges() {
        block.makeInvisible();
        assertFalse(block.isVisible());
        block.makeVisible();
        assertTrue(block.isVisible());
        block.makeInvisible();
        assertFalse(block.isVisible());
    }
}