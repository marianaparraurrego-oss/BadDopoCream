package domain;

import java.awt.Color;
/**
 * Representa el jugador que puede
 * moverse, disparar y romper hielo
 */
public class IceCream implements BreakIce{
	private int gridX, gridY;
	private Color color;
	private int direction = 2; //0 = arriba, 1 = derecha, 2 = abajo, 3= izquierda
	private Board board;
	private int breakBlocks = 3;
	/**
	 * Crea el jugador
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
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	/**
	 * Mueve al jugador
	 * @param direction
	 */
	public void move(int direction) {
		this.direction = direction;
		int newX = gridX;
		int newY = gridY;
		
		switch(direction) {
			case 0: newY--; break; //Arriba
			case 1: newX++; break; //Derecha
			case 2: newY++; break; //Abajo
			case 3: newX--; break; //Izquierda
		}
		if(board.canMoveTo(newX, newY)) {
			gridX = newX;
			gridY = newY;
		}
	}
	/**
	 * Dispara un bloque de hielo
	 * @return
	 */
	public Block shootIce() {
		int blockX = gridX;
		int blockY = gridY;
		
		switch(direction) {
			case 0: blockY--; break; //Arriba
			case 1: blockX++; break; //Derecha
			case 2: blockY++; break; //Abajo
			case 3: blockX--; break; //Izquierda
		}
		
		board.setBlock(blockX, blockY);
		return new Block(blockX, blockY);
		
	}
	/**
	 * Rompe bloques
	 */
	@Override
	public boolean breakBlock(int direction) {
		if(!canBreakBlocks()) return false;
		
		int breakX = gridX;
		int breakY = gridY;
		
		switch(direction) {
			case 0: breakY--; break; //Arriba
			case 1: breakX++; break; //Derecha
			case 2: breakY++; break; //Abajo
			case 3: breakX--; break; //Izquierda
		}
		for(int i = 0; i < breakBlocks; i++) {
			if(board.hasBlock(breakX, breakY)) {
				board.removeBlock(breakX, breakY);
			}else {
				break;
			}
			switch(direction) {
				case 0: breakY--; break; //Arriba
				case 1: breakX++; break; //Derecha
				case 2: breakY++; break; //Abajo
				case 3: breakX--; break; //Izquierda
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
