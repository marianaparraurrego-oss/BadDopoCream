package domain;
import java.awt.Color;
import java.io.Serializable;
/**
 * Enemigo que patrulla un area
 */
public class PatrollingEnemy extends Enemy implements Serializable {
	private static final long serialVersionUID = 1L;
	private int minX, maxX, minY, maxY;
	private int moveCounter = 0;
	private int moveLimit = 3; //Moverse hasta cambiar de dirrecion
	/**
	 * Crea un enemigo patrullante
	 * @param gridX
	 * @param gridY
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 */
	public PatrollingEnemy(int gridX, int gridY, int minX, int maxX, int minY, int maxY) {
		super(gridX, gridY, new Color(100, 100, 100));
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
	/**
	 * MOvimiento automatico
	 */
	@Override
	public void walk(IceCream iceCream) {
		moveCounter++;
		
		if(moveCounter >= moveLimit) {
			moveCounter = 0;
			direction = (direction + 1) % 4;
		}
		
		int newX = gridX;
		int newY = gridY;
		
		switch(direction) {
			case UP: newY--; break; //Arriba
			case RIGHT: newX++; break; //Derecha
			case DOWN: newY++; break; //Abajo
			case LEFT: newX--; break; //Izquierda
		}
		
		//Validar limites de patrulla
		if(newX >= minX && newX <= maxX && newY >= minY && newY <= maxY && board.canMoveTo(newX, newY)) {
			gridX= newX;
			gridY= newY;
		}else {
			moveCounter = moveLimit;
		}
	}
		

	@Override
	public String getType() {
		return "Troll";
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
