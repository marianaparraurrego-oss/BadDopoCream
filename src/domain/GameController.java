package domain;
import java.awt.Color;
/**
 * Controla la logica principal del juego, incluyendo
 * movimiento, enemigos, colisiones, tiempos y progreso
 */
public class GameController {
	private Board board;
	private int level;
	private int score;
	private int scorePlayer1;
	private int scorePlayer2; 
	private int fruitsCollected;
	private long startTime;
	private int timeLimit = 180;
	private boolean gameRunning;
	private boolean gamePaused;
	private int fruitsToCollect = 16;
	private int currentPlayerDirection = 2;
	private int currentPlayer2Direction = 2;
	private String gameMode;
	private Color iceCreamColor;
	private Color iceCreamColor2;
	
	private int currentWave = 0;
	private boolean waveInProgress = false;
	/**
	 * Crea un controlador para un nivel determinado
	 * @param level
	 * @param gameMode
	 * @param iceCreamColor
	 * @param player2ControlsEnemy
	 */
	public GameController(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2 ) {
		this.level =level;
		this.gameMode = gameMode;
		this.iceCreamColor = iceCreamColor;
		this.iceCreamColor2 = iceCreamColor2;
		
		this.fruitsCollected = 0;
		this.score = 0; 
		this.scorePlayer1 = 0;
		this.scorePlayer2 = 0;
		this.gameRunning = true;
		this.gamePaused = false;
		
		board = new Board();
		board.level1(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
		board.getIceCream().setBoard(board);
		
		if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
			board.getIceCream2().setBoard(board);
		}
		setupLevel1();
		
		for(Enemy e: board.getEnemies()) {
			e.setBoard(board);
		}
		
		startTime= System.currentTimeMillis();
	}
	
	/**
	 * Configura el nivel 1 con enemigos y frutas en oleadas
	 */
	private void setupLevel1() {
		// Enemigos (aparecen desde el inicio)
		board.addEnemy(new PatrollingEnemy(2, 2, 1, board.getCols()-2, 1, board.getRows()-2));
		board.addEnemy(new Pot(10, 2));
		
		// Obstáculos
		board.addBonfire(new Bonfire(12, 8));
		board.addHotTile(new HotTile(14, 10));
		
		// Primera oleada de frutas
		spawnWave();
	}
	/**
	 * Genera la oleada de frutas
	 */
	private void spawnWave() {
		if(currentWave >= 2) return; // Solo 2 oleadas
		
		waveInProgress = true;
		
		if(currentWave == 0) {
			// Primera oleada: 8 Uvas
			int[][] grapesPos = {
				{2,8},{4,8},{6,8},{8,8},
				{10,8},{2,9},{4,9},{6,9}
			};
			
			for(int[] pos: grapesPos) {
				board.addFruit(new Grapes(pos[0], pos[1]));
			}
		} else if(currentWave == 1) {
			// Segunda oleada: 8 Bananas
			int[][] bananasPos = {
				{8,9},{10,9},{2,10},{4,10},
				{6,10},{8,10},{10,10},{2,11}
			};
			
			for(int[] pos: bananasPos) {
				board.addFruit(new Banana(pos[0], pos[1]));
			}
			
			Cherry cherry1 = new Cherry(8, 10);
			cherry1.setBoard(board);
			board.addFruit(cherry1);
			
			Cherry cherry2 = new Cherry(10, 10);
			cherry2.setBoard(board);
			board.addFruit(cherry2);
			
			// Agregar Pineapple con referencia al IceCream y Board
			Pineapple pineapple = new Pineapple(2, 11);
			pineapple.setIceCream(board.getIceCream());
			pineapple.setBoard(board);
			board.addFruit(pineapple);
		}
		
		currentWave++;
	}
	/**
	 * Actualiza el estado del juego
	 */
	public void update() {
		if(!gameRunning || gamePaused) return;
		// Actualizar fogatas
		for(Bonfire bonfire : board.getBonfires()) {
			bonfire.update();
			
			if(bonfire.killsIceCream(board.getIceCream())) {
				resetLevel();
				return;
				}
		}
				
		// Enemigos automáticamente
		for(Enemy enemy: board.getEnemies()) {
			enemy.walk(board.getIceCream());
		}
				
		// Mover frutas
		for(Fruit fruit : board.getFruits()) {
			fruit.move();
		}
		 
		//Colisiones
		
		// Colisiones con frutas - Jugador 1
		checkFruitCollisions(board.getIceCream(), 1);
				
		// Colisiones con frutas - Jugador 2 (solo en PvP)
		if(gameMode.equals("PvP")) {
			checkFruitCollisions(board.getIceCream2(), 2);
		}
		
		// Verifica si se completó la oleada actual
		if(waveInProgress && board.getFruits().isEmpty() && currentWave < 2) {
			waveInProgress = false;
			spawnWave(); // Genera la siguiente oleada
		}
		
		for(Enemy enemy : board.getEnemies()) {
			if(board.getIceCream().getGridX() == enemy.getGridX() &&
				board.getIceCream().getGridY() == enemy.getGridY()) {
				resetLevel();
			}
		}
		
		// Colisiones con enemigos - Jugador 2
		if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
			for(Enemy enemy : board.getEnemies()) {
				if(board.getIceCream2().getGridX() == enemy.getGridX() &&
						board.getIceCream2().getGridY() == enemy.getGridY()) {
						resetLevel();
						return;
				}
			}
		}
		
		if(fruitsCollected >= fruitsToCollect) {
			gameRunning = false;
		}
		
		if(getTimeRemaining () <= 0) {
			resetLevel();
		}
	}
	
	/**
	 * Verifica colisiones con frutas para un jugador específico
	 * @param player El jugador (IceCream)
	 * @param playerNumber 1 o 2
	 */
	private void checkFruitCollisions(IceCream player, int playerNumber) {  // ← AQUÍ - Acepta jugador específico
		for(int i = board.getFruits().size() - 1; i >= 0; i--) {
			Fruit f = board.getFruits().get(i);
			if(f.isVisibleFruit() &&
			   player.getGridX() == f.getGridX() &&  // ← Usa el jugador pasado como parámetro
			   player.getGridY() == f.getGridY()) {
				
				// ... código de verificación de cactus ...
				
				// Calcular puntos según el tipo de fruta
				int points = 0;
				if(f instanceof Grapes) {
					points = 50;
				} else if(f instanceof Banana) {
					points = 100;
				} else if(f instanceof Cherry) {
					points = 150;
				} else if(f instanceof Pineapple) {
					points = 200;
				} else if(f instanceof Cactus) {
					points = 250;
				}
				
				// Asignar puntos según el modo de juego
				if(gameMode.equals("PvP")) {
					if(playerNumber == 1) {  // ← AQUÍ - Asigna puntos según el jugador
						scorePlayer1 += points;
					} else {
						scorePlayer2 += points;
					}
				} else {
					score += points;
				}
				
				board.getFruits().remove(i);
			}
		}
	}
	/**
	 * Mueve al jugador
	 * @param direction
	 */
	public void movePlayer(int direction) {
		currentPlayerDirection = direction;
		board.getIceCream().move(direction);
		
	}
	
	/**
	 * Mueve al jugador 2
	 * @param direction
	 */
	public void movePlayer2(int direction) {  // ← AQUÍ
		currentPlayer2Direction = direction;
		if(gameMode.equals("PvP")) {
			board.getIceCream2().move(direction);
		}
	}
	/**
	 * Realiza un disparo de hielo en la direccion actual
	 */
	public void shootIce() {
		int direction = currentPlayerDirection;
		IceCream iceCream = board.getIceCream();
		int targetX = iceCream.getGridX();
		int targetY = iceCream.getGridY();
		
		switch(direction) {
			case 0: targetY--; break; //Arriba
			case 1: targetX++; break; //Derecha
			case 2: targetY++; break; //Abajo
			case 3: targetX--; break; //Izquierda
			}
		if(board.hasBlock(targetX, targetY)) {
			breakIce();
		}else {
			board.getIceCream().shootIce();
		}
		
	}
	
	/**
	 * Realiza un disparo de hielo en la direccion actual - Jugador 2
	 */
	public void shootIce2() {  // ← AQUÍ
		if(!gameMode.equals("PvP")) return;
		
		int direction = currentPlayer2Direction;
		IceCream iceCream2 = board.getIceCream2();
		int targetX = iceCream2.getGridX();
		int targetY = iceCream2.getGridY();
		
		switch(direction) {
			case 0: targetY--; break; // Arriba
			case 1: targetX++; break; // Derecha
			case 2: targetY++; break; // Abajo
			case 3: targetX--; break; // Izquierda
		}
		
		if(board.hasBlock(targetX, targetY)) {
			board.getIceCream2().breakBlock(currentPlayer2Direction);
		} else {
			board.getIceCream2().shootIce();
		}
	}
	
	/**
	 * Intenta romper uno o mas bloques enfrente
	 */
	public void breakIce() {
		board.getIceCream().breakBlock(currentPlayerDirection);
	}
	/**
	 * Reinicia el nivel
	 */
	public void resetLevel() {
		fruitsCollected = 0;
		score = 0;
		currentWave = 0;
		waveInProgress = false;
		startTime = System.currentTimeMillis();
		gameRunning = true;
		gamePaused = false;
		
		board = new Board();
		board.level1(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
		board.getIceCream().setBoard(board);
		
		setupLevel1();
	}
	
	public int getFruitsCollected() {
		return fruitsCollected;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getTimeRemaining() {
		long elapsed = ( System.currentTimeMillis() - startTime) / 1000;
		return (int)(timeLimit-elapsed);
	}
	
	public boolean isGameRunning() {
		return gameRunning;
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}
	
	public void pauseGame() {
		gamePaused = !gamePaused;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getTotalFruits() {
		return fruitsToCollect;
	}
	
	public boolean didWin() {
		return fruitsCollected >= fruitsToCollect;
	}
	
	public String getGameMode() {
		return gameMode;
	}
	
	public int getScorePlayer1() { 
		return scorePlayer1;
	}

	public int getScorePlayer2() {  
		return scorePlayer2;
	}
	
	public int getLevel() {
		return level;
	}
}

