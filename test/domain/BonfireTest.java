package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

class BonfireTest {
    
    private Bonfire bonfire;
    private IceCream iceCream;
    
    @BeforeEach
    void setUp() {
        bonfire = new Bonfire(10, 10);
        iceCream = new IceCream(5, 5, Color.BLUE);
    }
    
    @Test
    void testConstructor() {
        assertEquals(10, bonfire.getGridX());
        assertEquals(10, bonfire.getGridY());
        assertTrue(bonfire.isActive());
    }
    
    @Test
    void testGetColor() {
        Color activeColor = bonfire.getColor();
        assertNotNull(activeColor);
        assertEquals(255, activeColor.getRed());
        assertEquals(69, activeColor.getGreen());
        assertEquals(0, activeColor.getBlue());
    }
    
    @Test
    void testExtinguish() {
        bonfire.extinguish();
        assertFalse(bonfire.isActive());
    }
    
    @Test
    void testGetColorWhenInactive() {
        bonfire.extinguish();
        Color inactiveColor = bonfire.getColor();
        assertEquals(128, inactiveColor.getRed());
        assertEquals(128, inactiveColor.getGreen());
        assertEquals(128, inactiveColor.getBlue());
    }
    
    @Test
    void testKillsIceCreamSamePosition() {
        iceCream.setPosition(10, 10);
        assertTrue(bonfire.killsIceCream(iceCream));
    }
    
    @Test
    void testKillsIceCreamDifferentPosition() {
        assertFalse(bonfire.killsIceCream(iceCream));
    }
    
    @Test
    void testKillsIceCreamWhenInactive() {
        iceCream.setPosition(10, 10);
        bonfire.extinguish();
        assertFalse(bonfire.killsIceCream(iceCream));
    }
    
    @Test
    void testUpdate() {
        bonfire.extinguish();
        bonfire.update();
        assertFalse(bonfire.isActive());
    }
    
    @Test
    void testUpdateRelight() throws InterruptedException {
        bonfire.extinguish();
        Thread.sleep(10100);
        bonfire.update();
        assertTrue(bonfire.isActive());
    }
    
    @Test
    void testMultipleExtinguish() {
        bonfire.extinguish();
        bonfire.extinguish();
        assertFalse(bonfire.isActive());
    }
    
    @Test
    void testKillsMultiplePositions() {
        IceCream ice1 = new IceCream(10, 10, Color.RED);
        IceCream ice2 = new IceCream(10, 11, Color.BLUE);
        IceCream ice3 = new IceCream(11, 10, Color.GREEN);
        
        assertTrue(bonfire.killsIceCream(ice1));
        assertFalse(bonfire.killsIceCream(ice2));
        assertFalse(bonfire.killsIceCream(ice3));
    }
}