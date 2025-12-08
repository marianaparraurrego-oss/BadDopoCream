package domain;

import java.awt.Color;
/**
 * Clase abstracta para representar las frutas
 */
public abstract class Fruit {
	protected int gridX, gridY;
	protected boolean visible;
	protected Color color;
	protected final int points;
	/**
	 * Crea una frura en la posicion especifica
	 * @param gridX coordenada x
	 * @param gridY coordenada y
	 */
	public Fruit(int gridX, int gridY, int points) {
		this.gridY = gridY;
		this.gridX = gridX;
		this.visible = true;
		this.points = points;
	}
	
	/**
	 * Hace visible la fruta
	 */
	public void makeVisible() {
		this.visible = true;
	}
	/**
	 * Hace invisible la fruta
	 */
	public void makeInvisible() {
		this.visible = false;
	}
	/**
	 * @return Si la fruta esta visible
	 */
	public boolean isVisibleFruit() {
		return visible;
	}
	/**
	 * @return tipo de fruta 
	 */
	public abstract String getType();
	/**
	 * Logica de movimiento
	 */
	public abstract void move();
	
	/**
	 * Obtiene los puntos que otorga la fruta
	 * @return puntos
	 */
	public final int getPoints() {
		return points;
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



