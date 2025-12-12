package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Representa un narval que recorre el espacio
 * y embiste cuando se alinea con el jugador
 */
public class Narval extends Enemy implements BreakIce, Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isCharging;
	private int chargeSpeed = 2;
	private int moveCounter = 0;
	
	/**
	 * Crea un narval
	 * @param gridX
	 * @param gridY
	 */
	public Narval(int gridX, int gridY) {
		super(gridX, gridY, new Color(135, 206, 250));
		this.isCharging = false;
		this.direction = 1;
	}

	@Override
	public void walk(IceCream iceCream) {
		if (iceCream == null || board == null) return;
		
		int targetX = iceCream.getGridX();
		int targetY = iceCream.getGridY();
		
		// Verifica si está alineado horizontal o verticalmente
		boolean alignedHorizontal = (gridY == targetY);
		boolean alignedVertical = (gridX == targetX);
		
		if ((alignedHorizontal || alignedVertical) && !isCharging) {
            // Inicia embestida
            isCharging = true;
            
            if (alignedHorizontal) {
                direction = (targetX > gridX) ? 1 : 3; // Derecha o Izquierda
            } else {
                direction = (targetY > gridY) ? 2 : 0; // Abajo o Arriba
            }
        }
        
        // Movimiento durante embestida
        if (isCharging) {
        	
            int speed = chargeSpeed;
            for (int i = 0; i < speed; i++) {
                int newX = gridX;
                int newY = gridY;
                
                switch(direction) {
                    case UP: newY--; break; // Arriba
                    case RIGHT: newX++; break; // Derecha
                    case DOWN: newY++; break; // Abajo
                    case LEFT: newX--; break; // Izquierda
                }
                
                // Si hay un bloque, lo rompe
                if (board.hasBlock(newX, newY)) {
                    board.removeBlock(newX, newY);
                }
                
                if (board.canMoveTo(newX, newY)) {
                    gridX = newX;
                    gridY = newY;
                } else {
                    // Termina la embestida si choca con algo
                    isCharging = false;
                    break;
                }
                
                // Termina embestida si alcanzó al helado
                if (gridX == targetX && gridY == targetY) {
                    isCharging = false;
                    break;
                }
            }
        } else {
            // Movimiento normal (patrulla)
            moveCounter++;
            if (moveCounter >= 20) {
                direction = (direction + 1) % 4;
                moveCounter = 0;
            }
            
            int newX = gridX;
            int newY = gridY;
            
            switch(direction) {
                case UP: newY--; break;
                case RIGHT: newX++; break;
                case DOWN: newY++; break;
                case LEFT: newX--; break;
            }
            
            if (board.canMoveTo(newX, newY) && !board.hasBlock(newX, newY)) {
                gridX = newX;
                gridY = newY;
            } else {
                moveCounter = 20; // Cambia de dirección
            }
        }
    }
	

	@Override
	public String getType() {
		return "Narwhal";
	}
	
	@Override
	public boolean breakBlock(int direction) {
		return isCharging;
	}
	
	 @Override
	    public boolean canBreakBlocks() {
	        return isCharging;
	    }

	    @Override
	    public int getBreakBlocksLine() {
	        return Integer.MAX_VALUE; // Rompe todos los bloques en su camino
	    }
	
	/**
	 * @return true si está embistiendo
	 */
	public boolean isCharging() {
		return isCharging;
	}

	@Override
	public int getAttackRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canAttack() {
		// TODO Auto-generated method stub
		return false;
	}
}