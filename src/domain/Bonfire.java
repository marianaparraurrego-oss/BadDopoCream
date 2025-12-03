package domain;

import java.awt.Color;

/**
 * Representa una fogata
 * Los enemigos no sufren daño, pero los helados son eliminados
 * Se puede apagar temporalmente con bloques de hielo
 */
public class Bonfire {
    private int gridX, gridY;
    private boolean isActive;
    private long extinguishTime;
    private static final long RELIGHT_TIME = 10000; // 10 segundos
    private Color activeColor = new Color(255, 69, 0); // Rojo-naranja
    private Color inactiveColor = new Color(128, 128, 128); // Gris
    
    /**
     * Crea una fogata
     * @param gridX posicion x
     * @param gridY posicion y
     */
    public Bonfire(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.isActive = true;
        this.extinguishTime = 0;
    }
    
    /**
     * Actualiza el estado de la fogata
     */
    public void update() {
        if (!isActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - extinguishTime >= RELIGHT_TIME) {
                isActive = true;
            }
        }
    }
    
    /**
     * Apaga la fogata temporalmente
     */
    public void extinguish() {
        if (isActive) {
            isActive = false;
            extinguishTime = System.currentTimeMillis();
        }
    }
    
    /**
     * @return true si la fogata está encendida
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Verifica si elimina al helado
     * @param iceCream
     * @return true si el helado está en la misma posición y la fogata está activa
     */
    public boolean killsIceCream(IceCream iceCream) {
        return isActive && 
               iceCream.getGridX() == gridX && 
               iceCream.getGridY() == gridY;
    }
    
    public int getGridX() {
        return gridX;
    }
    
    public int getGridY() {
        return gridY;
    }
    
    public Color getColor() {
        return isActive ? activeColor : inactiveColor;
    }
}