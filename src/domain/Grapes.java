package domain;

import java.awt.Color;
/**
 * Representa una fruta tipo uva
 * No se muve
 */
public class Grapes extends Fruit {
	/**
	 * Crea un grupo de uvas
	 * @param gridX
	 * @param gridY
	 */
	public Grapes(int gridX, int gridY) {
		super(gridX, gridY);
		this.color = Color.MAGENTA;	
	}

	@Override
	public String getType() {
		return "Grapes";
	}

	@Override
	public void move() {
		// do not move
	}
	@Override
	public int getPoints() {
		return 50;
	}

}
