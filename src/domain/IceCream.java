package domain;

import java.awt.Color;

/**
 * Representa el jugador que puede moverse, disparar y romper hielo
 */
public class IceCream implements BreakIce {

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	protected int gridX;
	protected int gridY;
	private Color color;
	public int direction = DOWN;
	public transient Board board;
	private int breakBlocks = 3;

	/**
	 * Crea el jugador
	 * 
	 * @param gridX
	 * @param gridY
	 * @param color
	 */
	public IceCream(int gridX, int gridY, Color color) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.color = color;
	}

	/**
	 * Asigna el tablero
	 * 
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Mueve al jugador
	 * 
	 * @param direction
	 */
	public void move(int direction) {
		this.direction = direction;
		int newX = gridX;
		int newY = gridY;

		switch (direction) {
		case UP:
			newY--;
			break; // Arriba
		case RIGHT:
			newX++;
			break; // Derecha
		case DOWN:
			newY++;
			break; // Abajo
		case LEFT:
			newX--;
			break; // Izquierda
		}
		if (board != null && board.canMoveTo(newX, newY) && !board.hasBlock(newX, newY)) {
			if (board.getGrid()[gridY][gridX] == 4) {
				board.getGrid()[gridY][gridX] = 0;
			}

			// Actualizar coordenadas
			gridX = newX;
			gridY = newY;

			// Marcar nueva posición en el grid
			board.getGrid()[gridY][gridX] = 4;
		}
	}

	/**
	 * Dispara un bloque de hielo
	 * 
	 * @return
	 */
	public Block shootIce() {
		if (board == null) {
            return null;
        }
		int blockX = gridX;
		int blockY = gridY;
		Block lastBlock = null;

		// Avanzar en la dirección actual
		while (true) {
			// Calcular siguiente posición
			switch (direction) {
			case UP:
				blockY--;
				break; // Arriba
			case RIGHT:
				blockX++;
				break; // Derecha
			case DOWN:
				blockY++;
				break; // Abajo
			case LEFT:
				blockX--;
				break; // Izquierda
			}

			// Verificar límites del tablero
			if (blockX < 0 || blockX >= board.getCols() || blockY < 0 || blockY >= board.getRows()) {
				break; // Salir si está fuera del tablero
			}

			// Si ya hay un bloque, detenerse
			if (board.hasBlock(blockX, blockY)) {
				break;
			}

			// Crear el bloque
			board.setBlock(blockX, blockY);
			lastBlock = new Block(blockX, blockY);
		}

		return lastBlock;
	}

	/**
	 * Rompe bloques
	 */
	@Override
	public boolean breakBlock(int direction) {
		if (!canBreakBlocks())
			return false;

		int breakX = gridX;
		int breakY = gridY;

		switch (direction) {
		case UP:
			breakY--;
			break; // Arriba
		case RIGHT:
			breakX++;
			break; // Derecha
		case DOWN:
			breakY++;
			break; // Abajo
		case LEFT:
			breakX--;
			break; // Izquierda
		}
		while (true) {
			if (breakX < 0 || breakX >= board.getCols() || breakY < 0 || breakY >= board.getRows()) {
				break;
			}

			if (board.hasBlock(breakX, breakY)) {
				board.removeBlock(breakX, breakY);
			} else {
				break; // Si no hay bloque, detenerse
			}
			switch (direction) {
			case UP:
				breakY--;
				break; // Arriba
			case RIGHT:
				breakX++;
				break; // Derecha
			case DOWN:
				breakY++;
				break; // Abajo
			case LEFT:
				breakX--;
				break; // Izquierda
			}
		}

		return true;
	}

	/**
	 * @return true si puede romper bloques
	 */
	@Override
	public boolean canBreakBlocks() {
		return true;
	}

	/**
	 * @return true si puede romper linea de bloques
	 */
	@Override
	public int getBreakBlocksLine() {
		return breakBlocks;
	}

	/**
	 * Elimina al jugador
	 */
	public void die() {
		gridX = -1;
		gridY = -1;
	}

	/**
	 * Establece la posición del helado
	 */
	public void setPosition(int x, int y) {
		this.gridX = x;
		this.gridY = y;
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
