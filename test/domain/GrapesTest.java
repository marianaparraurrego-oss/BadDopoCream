package domain;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrapesTest {
    private Grapes grapes;

    @BeforeEach

    public void setUp(){
        grapes = new Grapes(2,8);
    }

    @Test
    public void testGrapesCreation(){
        assertEquals(2, grapes.getGridX());
        assertEquals(8, grapes.getGridY());

        assertNotNull(grapes.getColor());
        assertTrue(grapes.isVisibleFruit());
    }

    @Test
    public void testGetType(){
        assertEquals("Grapes", grapes.getType());
    }

    @Test
    void testPoints() {
        assertEquals(50, grapes.getPoints());
    }
    
    @Test
    public void testMove(){
        int originalX = grapes.getGridX();
        int originalY = grapes.getGridY();
        grapes.move();
        assertEquals(originalX, grapes.getGridX());
        assertEquals(originalY, grapes.getGridY());
    }
    
    @Test
    void testVisibility() {
        assertTrue(grapes.isVisibleFruit());
        grapes.makeInvisible();
        assertFalse(grapes.isVisibleFruit());
        grapes.makeVisible();
        assertTrue(grapes.isVisibleFruit());
    }
    
}