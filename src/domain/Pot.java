package domain;

import java.awt.Color;

/**
 * Representa una maceta que persigue al jugador
 * No puede romper bloques
 */
public class Pot extends Enemy {
	
	/**
	 * Crea una maceta
	 * @param gridX
	 * @param gridY
	 */
	public Pot(int gridX, int gridY) {
		super(gridX, gridY, new Color(139, 69, 19));
	}

	@Override
	public void walk(IceCream iceCream) {
		if (iceCream == null || board == null) return;
		
		int targetX = iceCream.getGridX();
		int targetY = iceCream.getGridY();
		
		int dx = targetX - gridX;
		int dy = targetY - gridY;
		
		// Decide si moverse horizontal o verticalmente
		int newX = gridX;
		int newY = gridY;
		
		 if (Math.abs(dx) > Math.abs(dy)) {
	            // Moverse horizontalmente
	            if (dx > 0) {
	                newX = gridX + 1;
	                direction = 1; // Derecha
	            } else if (dx < 0) {
	                newX = gridX - 1;
	                direction = 3; // Izquierda
	            }
	        } else {
	            // Moverse verticalmente
	            if (dy > 0) {
	                newY = gridY + 1;
	                direction = 2; // Abajo
	            } else if (dy < 0) {
	                newY = gridY - 1;
	                direction = 0; // Arriba
	            }
	        }
		
		// Solo se mueve si no hay bloques
		 if (board.canMoveTo(newX, newY) && !board.hasBlock(newX, newY)) {
	            gridX = newX;
	            gridY = newY;
	        } else {
	            // Si está bloqueado, intenta moverse en la otra dirección
	            if (Math.abs(dx) > Math.abs(dy)) {
	                // Intentar vertical
	                if (dy > 0) {
	                    newY = gridY + 1;
	                } else if (dy < 0) {
	                    newY = gridY - 1;
	                }
	                newX = gridX;
	            } else {
	                // Intentar horizontal
	                if (dx > 0) {
	                    newX = gridX + 1;
	                } else if (dx < 0) {
	                    newX = gridX - 1;
	                }
	                newY = gridY;
	            }
	            
	            if (board.canMoveTo(newX, newY) && !board.hasBlock(newX, newY)) {
	                gridX = newX;
	                gridY = newY;
	            }
	        }
	    }

	@Override
	public String getType() {
		return "Pot";
	}
}