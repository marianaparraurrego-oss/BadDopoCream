package domain;

import java.awt.Color;
/**
 * Representa una fruta tipo banana dentro del tablero
 * No se mueve
 */
public class Banana extends Fruit {
	
	public static final int bananaPoints = 100;
	/**
	 * Crea una Banana
	 * @param gridX posicion x
	 * @param gridY posicion y
	 */
	public Banana(int gridX, int gridY) {
		super(gridX,gridY, bananaPoints);
		this.color = Color.YELLOW;
	}
	/**
	 * @return nombre del tipo de fruta
	 */
	@Override
	public String getType() {
		return "Banana";
	}

	@Override
	public void move() {
		// banana do not move
	}

}
