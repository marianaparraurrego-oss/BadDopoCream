package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CactusTest {

	private Cactus cactus;
    private IceCream iceCream;
    
    @BeforeEach
    void setUp() {
        cactus = new Cactus(5, 5);
        iceCream = new IceCream(3, 3, Color.BLUE);
    }
    
    @Test
    void testConstructor() {
        assertEquals(5, cactus.getGridX());
        assertEquals(5, cactus.getGridY());
        assertFalse(cactus.hasSpikes());
        assertEquals(Cactus.cactusPoints, cactus.getPoints());
        assertTrue(cactus.isVisibleFruit());
    }
    
    @Test
    void testGetType() {
        assertEquals("Cactus", cactus.getType());
    }
    
    @Test
    void testInitialColor() {
        Color color = cactus.getColor();
        assertEquals(34, color.getRed());
        assertEquals(139, color.getGreen());
        assertEquals(34, color.getBlue());
    }
    
    @Test
    void testHasSpikesInitially() {
        assertFalse(cactus.hasSpikes());
    }
    
    @Test
    void testCanBeCollectedInitially() {
        assertTrue(cactus.canBeCollected());
    }
    
    @Test
    void testMoveDoesNotChangePosition() {
        int x = cactus.getGridX();
        int y = cactus.getGridY();
        
        cactus.move();
        
        assertEquals(x, cactus.getGridX());
        assertEquals(y, cactus.getGridY());
    }
    
    @Test
    void testHurtsIceCreamDifferentPosition() {
        assertFalse(cactus.hurtsIceCream(iceCream));
    }
    
    @Test
    void testHurtsIceCreamSamePositionNoSpikes() {
        iceCream.setPosition(5, 5);
        assertFalse(cactus.hurtsIceCream(iceCream));
    }
    
    @Test
    void testHurtsIceCreamNull() {
        assertFalse(cactus.hurtsIceCream(null));
    }
    
    @Test
    void testPoints() {
        assertEquals(250, cactus.getPoints());
    }
    
    @Test
    void testVisibility() {
        assertTrue(cactus.isVisibleFruit());
        cactus.makeInvisible();
        assertFalse(cactus.isVisibleFruit());
    }
    
    @Test
    void testCanBeCollectedWithoutSpikes() {
        assertTrue(cactus.canBeCollected());
    }
}