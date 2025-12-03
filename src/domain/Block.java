package domain;
/**
 * Representa el bloque de hielo que puede ser puesto o destruido por el jugador
 */
public class Block {
	private int gridX, gridY;
	private boolean visible;
	/**
	 * Crea un bloque en la posicion dada
	 * @param gridX posicion x
	 * @param gridY posicion y
	 */
	public Block(int gridX, int gridY) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.visible = true;
	}
	/**
	 * Hace visible el bloque
	 */
	public void create() {
		this.visible = true;
	}
	/**
	 * Marca el bloque como roto
	 */
	public void breakBlock() {
		this.visible = false;
	}
	/**
	 * El bloque se vuelve visible
	 */
	public void makeVisible() {
		this.visible = true;
	}
	/**
	 * El bloque se vuelve invisible
	 */
	public void makeInvisible() {
		this.visible = false;
	}
	/**
	 * @return true si el bloque esta visible
	 */
	public boolean isVisible() {
		return visible;
	}
	/**
	 * @return coordenada x
	 */
	public int getGridX() {
		return gridX;
	}
	/**
	 * @return coordenada y
	 */
	public int getGridY() {
		return gridY;
	}

}
