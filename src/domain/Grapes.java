package domain;

import java.awt.Color;
import java.io.Serializable;
/**
 * Representa una fruta tipo uva
 * No se muve
 */
public class Grapes extends Fruit implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int grapesPoints = 50;
	/**
	 * Crea un grupo de uvas
	 * @param gridX
	 * @param gridY
	 */
	public Grapes(int gridX, int gridY) {
		super(gridX, gridY, grapesPoints);
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

}
