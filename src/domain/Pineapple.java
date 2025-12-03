package domain;

import java.awt.Color;

/**
 * Representa una fruta tipo piña
 * Se mueve cuando el helado se mueve
 */
public class Pineapple extends Fruit {
    private IceCream iceCream;
    private int lastIceCreamX;
    private int lastIceCreamY;
    private Board board;
    
    /**
     * Crea una piña
     * @param gridX posicion x
     * @param gridY posicion y
     */
    public Pineapple(int gridX, int gridY) {
        super(gridX, gridY);
        this.color = new Color(255, 215, 0); // Color dorado
    }
    
    /**
     * Asigna el helado para seguir sus movimientos
     * @param iceCream
     */
    public void setIceCream(IceCream iceCream) {
        this.iceCream = iceCream;
        if (iceCream != null) {
            this.lastIceCreamX = iceCream.getGridX();
            this.lastIceCreamY = iceCream.getGridY();
        }
    }
    
    /**
     * Asigna el tablero
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
    @Override
    public String getType() {
        return "Pineapple";
    }

    @Override
    public void move() {
        if (iceCream == null || board == null) return;
        
        int currentIceCreamX = iceCream.getGridX();
        int currentIceCreamY = iceCream.getGridY();
        
        // Si el helado se movió, la piña se mueve en la MISMA dirección
        if (currentIceCreamX != lastIceCreamX || currentIceCreamY != lastIceCreamY) {
            int deltaX = currentIceCreamX - lastIceCreamX;
            int deltaY = currentIceCreamY - lastIceCreamY;
            
            int newX = gridX + deltaX;
            int newY = gridY + deltaY;
            
            // Solo se mueve si la nueva posición es válida y no hay bloques
            if (board.canMoveTo(newX, newY) && !board.hasBlock(newX, newY)) {
                this.gridX = newX;
                this.gridY = newY;
            }
            
            lastIceCreamX = currentIceCreamX;
            lastIceCreamY = currentIceCreamY;
        }
    }
    
    /**
     * @return puntos que otorga la piña
     */
    public int getPoints() {
        return 200;
    }
}