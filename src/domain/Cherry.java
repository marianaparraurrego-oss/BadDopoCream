package domain;

import java.awt.Color;
import java.util.Random;

/**
 * Representa una cereza que se teletransporta cada 20 segundos
 */
public class Cherry extends Fruit {
	private long lastTeleport;
	private int teleportInterval = 20000; // 20 segundos
	private Board board;
	private Random random;
	public static final int cherryPoints = 150;
	/**
	 * Crea una cereza
	 * @param gridX
	 * @param gridY
	 */
	public Cherry(int gridX, int gridY) {
		super(gridX, gridY, cherryPoints);
		this.color = Color.RED;
		this.lastTeleport = System.currentTimeMillis();
		this.random = new Random();
	}
	
	/**
	 * Asigna el tablero para poder teletransportarse
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public String getType() {
		return "Cherry";
	}

	@Override
	public void move() {
		if (board == null) return;
		
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTeleport >= teleportInterval) {
			teleport();
			lastTeleport = currentTime;
		}
	}
	
	/**
	 * Teletransporta la cereza a una posición aleatoria libre
	 */
	private void teleport() {
		int maxAttempts = 100;
		int attempts = 0;
		
		while (attempts < maxAttempts) {
			int newX = random.nextInt(board.getCols() - 2) + 1;
			int newY = random.nextInt(board.getRows() - 2) + 1;
			
			if (board.canMoveTo(newX, newY) && !board.hasBlock(newX, newY)) {
                this.gridX = newX;
                this.gridY = newY;
                break;
			}
			attempts++;
		}
	}
	
	
}