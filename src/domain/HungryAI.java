package domain;

import java.awt.Color;

public class HungryAI extends IceCreamAI {
    
    public HungryAI(int gridX, int gridY, Color color) {
        super(gridX, gridY, color, "hungry");
    }
    
    @Override
    public void makeDecision() {
        if (board == null) return;
        
        Fruit nearestFruit = findNearestFruit();
        if (nearestFruit != null) {
            // Priorizar frutas de alto valor
            moveTowards(nearestFruit.getGridX(), nearestFruit.getGridY());
            
            // Ocasionalmente disparar para crear caminos
            shootIfPossible();
        } else {
            // Si no hay frutas, moverse aleatoriamente
            moveRandomly();
        }
    }
}