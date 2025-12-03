package domain;

import java.awt.Color;
/**
 * Representa una fruta tipo banana dentro del tablero
 * No se mueve
 */
public class Banana extends Fruit {
	/**
	 * Crea una Banana
	 * @param gridX posicion x
	 * @param gridY posicion y
	 */
	public Banana(int gridX, int gridY) {
		super(gridX,gridY);
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


	@Override
	public int getPoints() {
		return 100;
	}

}
