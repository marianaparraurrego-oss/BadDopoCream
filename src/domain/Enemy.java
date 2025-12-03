package domain;

import java.awt.Color;
/**
 * Clase abstracta para definir enemigos
 * Contiene comportamiento basico para atacar y moverse
 */
public abstract class Enemy implements Attack {
	protected int gridX;
	protected int gridY;
	private Color color;
	protected int direction = 1; //0 = arriba, 1 = derecha, 2 = abajo, 3= izquierda
	protected Board board;
	/**
	 * Crea un enemigo en la posicion indicada
	 * @param gridX coordenada x
	 * @param gridY coordenada y
	 * @param color 
	 */
	public Enemy(int gridX, int gridY, Color color) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.color = color;
	}
	/**
	 * Asigna el enemigo en el yablero
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	/**
	 * Metodo abstracto para caminar
	 * @param iceCream referencia al jugador
	 */
	public abstract void walk(IceCream iceCream);
	/**
	 * 
	 * @return nombre del tipo de enemigo
	 */
	public abstract String getType();
	
	/**
	 * Intenta atacar al jugador si esta cerca
	 * @param target icecream
	 * @return true si logro atacarlo
	 */
	@Override
	public boolean attack(IceCream target) {
		int distance = Math.abs(target.getGridX()-gridX) + Math.abs(target.getGridY()- gridY);
		if(distance <= getAttackRange() && canAttack()) {
			target.die();
			return true;
		}
		return false;
	}
	/**
	 * @return rango de ataque del enemigo 
	 */
	
	@Override
	public int getAttackRange() {
		return 1;
	}
	/**
	 * @return si el enemigo puede atacar
	 */
	@Override
	public boolean canAttack() {
		return true;
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
	
	public int getDirection() {
		return direction;
	}
}
