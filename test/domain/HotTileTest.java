package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotTileTest {

private HotTile hotTile;
    
    @BeforeEach
    void setUp() {
        hotTile = new HotTile(8, 12);
    }
    
    @Test
    void testConstructor() {
        assertEquals(8, hotTile.getGridX());
        assertEquals(12, hotTile.getGridY());
        assertNotNull(hotTile.getColor());
    }
    
    @Test
    void testMeltsBlockSamePosition() {
        assertTrue(hotTile.meltsBlock(8, 12));
    }
    
    @Test
    void testMeltsBlockDifferentX() {
        assertFalse(hotTile.meltsBlock(9, 12));
    }
    
    @Test
    void testMeltsBlockDifferentY() {
        assertFalse(hotTile.meltsBlock(8, 13));
    }
    
    @Test
    void testMeltsBlockDifferentPosition() {
        assertFalse(hotTile.meltsBlock(5, 5));
    }
    
    @Test
    void testGetColor() {
        Color color = hotTile.getColor();
        assertNotNull(color);
        assertEquals(255, color.getRed());
        assertEquals(140, color.getGreen());
        assertEquals(0, color.getBlue());
    }
    
    @Test
    void testMultipleBlockChecks() {
        assertTrue(hotTile.meltsBlock(8, 12));
        assertFalse(hotTile.meltsBlock(7, 12));
        assertFalse(hotTile.meltsBlock(8, 11));
        assertFalse(hotTile.meltsBlock(0, 0));
    }
}