package domain;

import java.awt.Color;

public class FearfulAI extends IceCreamAI {
    
    public FearfulAI(int gridX, int gridY, Color color) {
        super(gridX, gridY, color, "fearful");
    }
    
    @Override
    public void makeDecision() {
        if (board == null) return;
        
        Enemy nearestEnemy = findNearestEnemy();
        
        if (nearestEnemy != null) {
            int distance = Math.abs(nearestEnemy.getGridX() - gridX) + 
                          Math.abs(nearestEnemy.getGridY() - gridY);
            
            if (distance <= 4) { // Si el enemigo está cerca, huir
                moveAwayFrom(nearestEnemy.getGridX(), nearestEnemy.getGridY());
                
                // Intentar crear barreras de hielo para protegerse
                if (distance <= 2 && random.nextInt(5) == 0) {
                    shootIce();
                }
                return;
            }
        }
        
        // Si no hay peligro inmediato, buscar frutas
        Fruit nearestFruit = findNearestFruit();
        if (nearestFruit != null) {
            // Verificar que la fruta esté en posición segura
            if (isPositionSafe(nearestFruit.getGridX(), nearestFruit.getGridY())) {
                moveTowards(nearestFruit.getGridX(), nearestFruit.getGridY());
            } else {
                moveRandomly();
            }
        } else {
            moveRandomly();
        }
    }
}