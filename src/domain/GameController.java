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
	//private int currentPlayerDirection = 2;
	//private int currentPlayer2Direction = 2;
	
	private int enemyMoveCounter = 0;
    private int enemyMoveDelay = 5;
    
	private String gameMode;
	private Color iceCreamColor;
	private Color iceCreamColor2;
	
	private int currentWave = 0;
	private boolean waveInProgress = false;
	private LevelConfiguration[] levelConfigs;

	/**
	 * Crea un controlador para un nivel determinado
	 * @param level
	 * @param gameMode
	 * @param iceCreamColor
	 * @param player2ControlsEnemy
	 */
	public GameController(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2, LevelConfiguration[] levelConfigs ) {
		this.level =level;
		this.levelConfigs = levelConfigs;
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
		setupLevel(level);
		//board.level1(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
		board.getIceCream().setBoard(board);
		
		if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
			board.getIceCream2().setBoard(board);
		}
		
		for(Enemy e: board.getEnemies()) {
			e.setBoard(board);
		}
		
		startTime= System.currentTimeMillis();
	}
	
	/**
	 * Configura el nivel especificado
	 */
	private void setupLevel(int level) {
		switch(level) {
			case 1:
				board.level1(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
				setupLevel1();
				break;
			case 2:
				board.level2(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
				setupLevel2();
				break;
			case 3:
				board.level3(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
				setupLevel3();
				break;
			default:
				board.level1(iceCreamColor, iceCreamColor2, gameMode.equals("PvP"));
				setupLevel1();
		}
	}
	
	/**
	 * Configura el nivel 1 con enemigos y frutas en oleadas
	 */
	private void setupLevel1() {
		// Enemigos (aparecen desde el inicio)
		if(levelConfigs != null && levelConfigs.length > 0) {
			LevelConfiguration config = levelConfigs[0];
			addEnemyByType(config.getEnemyType(), 2, 2);
			addObstaclesByType(config.getObstacleType());
		} else {
			// Fallback por defecto
			board.addEnemy(new PatrollingEnemy(2, 2, 1, board.getCols()-2, 1, board.getRows()-2));
			board.addBonfire(new Bonfire(12, 8));
			board.addHotTile(new HotTile(14, 10));
		}
		
		fruitsToCollect = 16;
		spawnWave();
	}
	
	/**
	 * Configura el nivel 2: Cerezas y Piñas
	 * Enemigos: 1 Maceta, 1 Calamar Naranja
	 * Obstáculos: 2 Fogatas, 2 Baldosas calientes
	 */
	private void setupLevel2() {
		// Enemigos
		if(levelConfigs != null && levelConfigs.length > 1) {
			LevelConfiguration config = levelConfigs[1];
			addEnemyByType(config.getEnemyType(), 3, 3);
			addObstaclesByType(config.getObstacleType());
		} else {
			// Fallback por defecto
			board.addEnemy(new Pot(3, 3));
			board.addBonfire(new Bonfire(8, 8));
			board.addBonfire(new Bonfire(12, 12));
			board.addHotTile(new HotTile(10, 6));
			board.addHotTile(new HotTile(6, 10));
		}
		
		fruitsToCollect = 12;
		spawnWave();
	}
	
	/**
	 * Configura el nivel 3: Cactus y todas las frutas
	 * Enemigos: 1 Narval, 1 Calamar Naranja, 1 Maceta
	 * Obstáculos: 3 Fogatas, 3 Baldosas calientes
	 */
	private void setupLevel3() {
		// Enemigos
		if(levelConfigs != null && levelConfigs.length > 2) {
			LevelConfiguration config = levelConfigs[2];
			addEnemyByType(config.getEnemyType(), 8, 8);
			addObstaclesByType(config.getObstacleType());
		} else {
			// Fallback por defecto
			board.addEnemy(new Narval(8, 8));
			board.addBonfire(new Bonfire(6, 6));
			board.addBonfire(new Bonfire(11, 6));
			board.addBonfire(new Bonfire(8, 12));
			board.addHotTile(new HotTile(4, 8));
			board.addHotTile(new HotTile(13, 8));
			board.addHotTile(new HotTile(8, 4));
		}
		
		fruitsToCollect = 14;
		spawnWave();
	}
	
	/**
	 * Genera la oleada de frutas según el nivel
	 */
	private void spawnWave() {
		if(level == 1) {
			spawnWaveLevel1();
		} else if(level == 2) {
			spawnWaveLevel2();
		} else if(level == 3) {
			spawnWaveLevel3();
		}
	}
	
	/**
	 * Oleadas del Nivel 1: Según configuración del usuario
	 */
	private void spawnWaveLevel1() {
		if(currentWave >= 2) return;
		
		waveInProgress = true;
		LevelConfiguration config = levelConfigs[0];
		
		if(currentWave == 0) {
			// Primera oleada: 8 frutas del tipo 1
			spawnFruitsByType(config.getFruit1Type(), new int[][]{
				{2,8},{4,8},{6,8},{8,8},
				{10,8},{2,9},{4,9},{6,9}
			});
		} else if(currentWave == 1) {
			// Segunda oleada: 8 frutas del tipo 2
			spawnFruitsByType(config.getFruit2Type(), new int[][]{
				{8,9},{10,9},{2,10},{4,10},
				{6,10},{8,10},{10,10},{2,11}
			});
		}
		currentWave++;
	}
	
	/**
	 * Oleadas del Nivel 2: Según configuración del usuario
	 */
	private void spawnWaveLevel2() {
		if(currentWave >= 2) return;
		
		waveInProgress = true;
		LevelConfiguration config = levelConfigs[1];
		
		if(currentWave == 0) {
			// Primera oleada: 6 frutas del tipo 1
			spawnFruitsByType(config.getFruit1Type(), new int[][]{
				{3,8},{5,8},{7,8},
				{9,8},{11,8},{13,8}
			});
		} else if(currentWave == 1) {
			// Segunda oleada: 6 frutas del tipo 2
			spawnFruitsByType(config.getFruit2Type(), new int[][]{
				{3,10},{5,10},{7,10},
				{9,10},{11,10},{13,10}
			});
		}
		currentWave++;
	}
	
	/**
	 * Oleadas del Nivel 3: Según configuración del usuario
	 */
	private void spawnWaveLevel3() {
		if(currentWave >= 2) return;
		
		waveInProgress = true;
		LevelConfiguration config = levelConfigs[2];
		
		if(currentWave == 0) {
			// Primera oleada: 7 frutas del tipo 1
			spawnFruitsByType(config.getFruit1Type(), new int[][]{
				{4,8},{8,8},{12,8},{8,11},
				{3,10},{7,10},{11,10}
			});
		} else if(currentWave == 1) {
			// Segunda oleada: 7 frutas del tipo 2
			spawnFruitsByType(config.getFruit2Type(), new int[][]{
				{4,9},{6,9},{10,9},{12,9},
				{5,11},{8,9},{11,11}
			});
		}
		currentWave++;
	}
	
	/**
	 * Genera frutas de un tipo específico en las posiciones dadas
	 */
	private void spawnFruitsByType(String type, int[][] positions) {
		for(int[] pos : positions) {
			Fruit fruit = createFruitByType(type, pos[0], pos[1]);
			if(fruit != null) {
				board.addFruit(fruit);
			}
		}
	}
	
	/**
	 * Crea una fruta según su tipo
	 */
	private Fruit createFruitByType(String type, int x, int y) {
		Fruit fruit = null;
		
		switch(type) {
			case "Grapes":
				fruit = new Grapes(x, y);
				break;
			case "Banana":
				fruit = new Banana(x, y);
				break;
			case "Cherry":
				Cherry cherry = new Cherry(x, y);
				cherry.setBoard(board);
				fruit = cherry;
				break;
			case "Pineapple":
				Pineapple pineapple = new Pineapple(x, y);
				pineapple.setIceCream(board.getIceCream());
				pineapple.setBoard(board);
				fruit = pineapple;
				break;
			case "Cactus":
				fruit = new Cactus(x, y);
				break;
		}
		
		return fruit;
	}
	
	/**
	 * Agrega un enemigo según su tipo
	 */
	private void addEnemyByType(String type, int x, int y) {
		Enemy enemy = null;
		
		switch(type) {
			case "Troll":
				enemy = new PatrollingEnemy(x, y, 1, board.getCols()-2, 1, board.getRows()-2);
				break;
			case "Pot":
				enemy = new Pot(x, y);
				break;
			case "OrangeSquid":
				enemy = new OrangeSquid(x, y);
				break;
			case "Narval":
				enemy = new Narval(x, y);
				break;
		}
		
		if(enemy != null) {
			board.addEnemy(enemy);
		}
	}
	
	/**
	 * Agrega obstáculos según el tipo seleccionado
	 */
	private void addObstaclesByType(String type) {
		switch(type) {
			case "Bonfire":
				board.addBonfire(new Bonfire(12, 8));
				board.addBonfire(new Bonfire(8, 12));
				break;
			case "HotTile":
				board.addHotTile(new HotTile(14, 10));
				board.addHotTile(new HotTile(10, 6));
				break;
			case "Both":
				board.addBonfire(new Bonfire(12, 8));
				board.addHotTile(new HotTile(14, 10));
				break;
		}
	}
	/**
	 * Actualiza el estado del juego
	 */
	public void update() {
		if(!gameRunning || gamePaused) return;
		
		 if(getTimeRemaining() <= 0) {
		        resetLevel();
		        return;
		    }
		// Actualizar fogatas
		for(Bonfire bonfire : board.getBonfires()) {
			bonfire.update();
			
			if(bonfire.killsIceCream(board.getIceCream())) {
				resetLevel();
				return;
				}
			//Verifica jugador 2
			if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
				if(bonfire.killsIceCream(board.getIceCream2())) {
					resetLevel();
					return;
				}
			}
		}
				
		// Enemigos automáticamente
		enemyMoveCounter++;
        if(enemyMoveCounter >= enemyMoveDelay) {
            enemyMoveCounter = 0;
            
            // Solo mover enemigos cuando el contador alcanza el delay
            for(Enemy enemy: board.getEnemies()) {
                enemy.walk(board.getIceCream());
            }
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
				return;
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
	
	 // Método para ajustar la velocidad globalmente
    public void setEnemyMoveDelay(int delay) {
        this.enemyMoveDelay = delay;
    }
	
	/**
	 * Verifica colisiones con frutas para un jugador específico
	 * @param player El jugador (IceCream)
	 * @param playerNumber 1 o 2
	 */
	private void checkFruitCollisions(IceCream player, int playerNumber) { 
		for(int i = board.getFruits().size() - 1; i >= 0; i--) {
			Fruit f = board.getFruits().get(i);
			if(f.isVisibleFruit() &&
			   player.getGridX() == f.getGridX() &&  // ← Usa el jugador pasado como parámetro
			   player.getGridY() == f.getGridY()) {
				
				if(f instanceof Cactus) {
			        Cactus cactus = (Cactus) f;
			        if(cactus.hasSpikes()) {
			            continue; // No recolectar si tiene púas
			        }
			    }
				
				// Calcular puntos según el tipo de fruta
				int points = f.getPoints();
				
				// Asignar puntos según el modo de juego
				if(gameMode.equals("PvP")) {
					if(playerNumber == 1) {  
						scorePlayer1 += points;
					} else {
						scorePlayer2 += points;
					}
				} else {
					score += points;
				}
				
				board.getFruits().remove(i);
				fruitsCollected++;
			}
		}
	}
	/**
	 * Mueve al jugador
	 * @param direction
	 */
	public void movePlayer(int direction) {
		//currentPlayerDirection = direction;
		board.getIceCream().move(direction);
		
	}
	
	/**
	 * Mueve al jugador 2
	 * @param direction
	 */
	public void movePlayer2(int direction) {  // ← AQUÍ
		//currentPlayer2Direction = direction;
		if(gameMode.equals("PvP")) {
			board.getIceCream2().move(direction);
		}
	}
	/**
	 * Realiza un disparo de hielo en la direccion actual
	 */
	public void shootIce() {
		IceCream iceCream = board.getIceCream();
		int direction = iceCream.getDirection();
		int targetX = iceCream.getGridX();
		int targetY = iceCream.getGridY();
		
		switch(direction) {
			case IceCream.UP: targetY--; break; //Arriba
			case IceCream.RIGHT: targetX++; break; //Derecha
			case IceCream.DOWN: targetY++; break; //Abajo
			case IceCream.LEFT: targetX--; break; //Izquierda
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
		
		IceCream iceCream2 = board.getIceCream2();
		int direction = iceCream2.getDirection();
		int targetX = iceCream2.getGridX();
		int targetY = iceCream2.getGridY();
		
		switch(direction) {
			case IceCream.UP: targetY--; break; // Arriba
			case IceCream.RIGHT: targetX++; break; // Derecha
			case IceCream.DOWN: targetY++; break; // Abajo
			case IceCream.LEFT: targetX--; break; // Izquierda
		}
		
		if(board.hasBlock(targetX, targetY)) {
			iceCream2.breakBlock(direction);
		} else {
			iceCream2.shootIce();
		}
	}
	
	/**
	 * Intenta romper uno o mas bloques enfrente
	 */
	public void breakIce() {
		IceCream iceCream = board.getIceCream();
		int direction = iceCream.getDirection();
		board.getIceCream().breakBlock(direction);
	}
	/**
	 * Reinicia el nivel
	 */
	public void resetLevel() {
		fruitsCollected = 0;
		score = 0;
		scorePlayer1 = 0;
		scorePlayer2 = 0;
		currentWave = 0;
		waveInProgress = false;
		startTime = System.currentTimeMillis();
		gameRunning = true;
		gamePaused = false;
		
		board = new Board();
		setupLevel(level);
		
		board.getIceCream().setBoard(board);
		if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
			board.getIceCream2().setBoard(board);
		}
	}
	
	/**
	 * Reinicia al nivel 1 (cuando muere o se acaba el tiempo)
	 */
	public void resetToLevel1() {
		// Volver al nivel 1 y resetear score
		level = 1;
		fruitsCollected = 0;
		score = 0;
		scorePlayer1 = 0;
		scorePlayer2 = 0;
		currentWave = 0;
		waveInProgress = false;
		startTime = System.currentTimeMillis();
		gameRunning = true;
		gamePaused = false;
		
		board = new Board();
		setupLevel(1);
		
		board.getIceCream().setBoard(board);
		if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
			board.getIceCream2().setBoard(board);
		}
		
		for(Enemy e: board.getEnemies()) {
			e.setBoard(board);
		}
	}
	
	/**
	 * Avanza al siguiente nivel
	 */
	public void nextLevel() {
		if(level < 3) {
			level++;
			fruitsCollected = 0;
			// MANTENER EL SCORE ACUMULADO
			currentWave = 0;
			waveInProgress = false;
			startTime = System.currentTimeMillis();
			gameRunning = true;
			gamePaused = false;
			
			board = new Board();
			setupLevel(level);
			
			board.getIceCream().setBoard(board);
			if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
				board.getIceCream2().setBoard(board);
			}
			
			for(Enemy e: board.getEnemies()) {
				e.setBoard(board);
			}
		}
	}
	
	/**
	 * Carga el estado del juego desde un GameState
	 */
	public void loadFromState(GameState state) {
		this.level = state.getLevel();
		this.gameMode = state.getGameMode();
		this.score = state.getScore();
		this.scorePlayer1 = state.getScorePlayer1();
		this.scorePlayer2 = state.getScorePlayer2();
		this.fruitsCollected = state.getFruitsCollected();
		this.currentWave = state.getCurrentWave();
		//this.waveInProgress = false;
		
		// Calcular tiempo restante
		this.startTime = System.currentTimeMillis() - state.getElapsedTime();
		
		// Recrear el tablero
		board = new Board();
		setupLevel(level);
		
		// Restaurar posiciones de helados
		board.getIceCream().setPosition(state.getIceCream1X(), state.getIceCream1Y());
		board.getIceCream().setBoard(board);
		
		if(gameMode.equals("PvP") || gameMode.equals("PvM")) {
			board.getIceCream2().setPosition(state.getIceCream2X(), state.getIceCream2Y());
			board.getIceCream2().setBoard(board);
		}
		
		// Limpiar frutas actuales y cargar las guardadas
		board.getFruits().clear();
		for(GameState.FruitData fruitData : state.getFruits()) {
			Fruit fruit = createFruitFromData(fruitData);
			if(fruit != null) {
				board.addFruit(fruit);
			}
		}
		
		// Cargar enemigos
		board.getEnemies().clear();
		for(GameState.EnemyData enemyData : state.getEnemies()) {
			Enemy enemy = createEnemyFromData(enemyData);
			if(enemy != null) {
				board.addEnemy(enemy);
				enemy.setBoard(board);
			}
		}
				
				// Cargar fogatas
		board.getBonfires().clear();
		for(GameState.BonfireData bonfireData : state.getBonfires()) {
			Bonfire bonfire = new Bonfire(bonfireData.gridX, bonfireData.gridY);
			if(!bonfireData.isActive) {
				bonfire.extinguish();
			}
			board.addBonfire(bonfire);
		}
		
		gameRunning = true;
		gamePaused = false;
	}
	
	/**
	 * Crea una fruta desde los datos guardados
	 */
	private Fruit createFruitFromData(GameState.FruitData data) {
		Fruit fruit = null;
		
		switch(data.type) {
			case "Grapes":
				fruit = new Grapes(data.gridX, data.gridY);
				break;
			case "Banana":
				fruit = new Banana(data.gridX, data.gridY);
				break;
			case "Cherry":
				Cherry cherry = new Cherry(data.gridX, data.gridY);
				cherry.setBoard(board);
				fruit = cherry;
				break;
			case "Pineapple":
				Pineapple pineapple = new Pineapple(data.gridX, data.gridY);
				pineapple.setIceCream(board.getIceCream());
				pineapple.setBoard(board);
				fruit = pineapple;
				break;
			case "Cactus":
				fruit = new Cactus(data.gridX, data.gridY);
				break;
		}
		
		return fruit;
	}
	
	/**
	 * Crea un enemigo desde los datos guardados
	 */
	private Enemy createEnemyFromData(GameState.EnemyData data) {
		Enemy enemy = null;
		
		switch(data.type) {
			case "PatrollingEnemy":
			case "Troll":
				enemy = new PatrollingEnemy(data.gridX, data.gridY, 1, board.getCols()-2, 1, board.getRows()-2);
				break;
			case "Pot":
				enemy = new Pot(data.gridX, data.gridY);
				break;
			case "OrangeSquid":
				enemy = new OrangeSquid(data.gridX, data.gridY);
				break;
			case "Narval":
				enemy = new Narval(data.gridX, data.gridY);
				break;
		}
		
		return enemy;
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
	
	public int getCurrentWave() {
		return currentWave; 
	}
	public long getStartTime() { 
		return startTime; 
	}
	
	public LevelConfiguration[] getLevelConfigs() {
	    return levelConfigs;
	}
}

