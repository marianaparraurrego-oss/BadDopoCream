package domain;

import java.awt.Color;

/**
 * Representa una baldosa caliente
 * Los bloques de hielo creados sobre ella se derriten inmediatamente
 */
public class HotTile {
    private int gridX, gridY;
    private Color color = new Color(255, 140, 0); // Naranja
    
    /**
     * Crea una baldosa caliente
     * @param gridX posicion x
     * @param gridY posicion y
     */
    public HotTile(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }
    
    /**
     * Verifica si derrite un bloque en esta posición
     * @param blockX coordenada x del bloque
     * @param blockY coordenada y del bloque
     * @return true si el bloque está sobre esta baldosa
     */
    public boolean meltsBlock(int blockX, int blockY) {
        return blockX == gridX && blockY == gridY;
    }
    
    public int getGridX() {
        return gridX;
    }
    
    public int getGridY() {
        return gridY;
    }
    
    public Color getColor() {
        return color;
    }
}
