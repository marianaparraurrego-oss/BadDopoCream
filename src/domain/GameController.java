package domain;

import java.awt.Color;

import exceptions.BadDopoCreamExceptions;

/**
 * Controla la logica principal del juego, incluyendo movimiento, enemigos,
 * colisiones, tiempos y progreso
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
	private int fruitsToCollect;

	private IceCreamAI aiPlayer1;
	private IceCreamAI aiPlayer2;
	private String ai1Profile = "expert";
	private String ai2Profile = "expert";
	private int aiDecisionCounter = 0;
	private int aiDecisionDelay = 3;
	// private int currentPlayerDirection = 2;
	// private int currentPlayer2Direction = 2;

	private int enemyMoveCounter = 0;
	private int enemyMoveDelay = 5;

	private String gameMode;
	private Color iceCreamColor;
	private Color iceCreamColor2;

	private int currentWave = 0;
	private boolean waveInProgress = false;
	private LevelConfiguration[] levelConfigs;

	/**
	 * Constructor con selección de perfiles de IA
	 */
	public GameController(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2,
			LevelConfiguration[] levelConfigs, String aiProfile1, String aiProfile2) {
		this.level = level;
		this.levelConfigs = levelConfigs;
		this.gameMode = gameMode;
		this.iceCreamColor = iceCreamColor;
		this.iceCreamColor2 = iceCreamColor2;
		this.ai1Profile = (aiProfile1 != null && !aiProfile1.isEmpty()) ? aiProfile1 : "expert";
		this.ai2Profile = (aiProfile2 != null && !aiProfile2.isEmpty()) ? aiProfile2 : "expert";

		this.fruitsCollected = 0;
		this.score = 0;
		this.scorePlayer1 = 0;
		this.scorePlayer2 = 0;
		this.gameRunning = true;
		this.gamePaused = false;

		board = new Board();
		setupLevel(level);

		if (gameMode.equals("PvM") || gameMode.equals("MvM")) {
			setupAI();

			if (aiPlayer1 != null) {
				board.replaceIceCream(aiPlayer1);
			} else {
				board.getIceCream().setBoard(board);
			}

			if (aiPlayer2 != null) {
				board.replaceIceCream2(aiPlayer2);
			} else if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
				board.getIceCream2().setBoard(board);
			}
		} else {
			board.getIceCream().setBoard(board);

			if (gameMode.equals("PvP")) {
				board.getIceCream2().setBoard(board);
			}
		}

		for (Enemy e : board.getEnemies()) {
			e.setBoard(board);
		}

		startTime = System.currentTimeMillis();
	}

	/**
	 * Crea un controlador para un nivel determinado
	 * 
	 * @param level
	 * @param gameMode
	 * @param iceCreamColor
	 * @param player2ControlsEnemy
	 */
	public GameController(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2,
			LevelConfiguration[] levelConfigs) {
		this.level = level;
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

		if (gameMode.equals("PvM") || gameMode.equals("MvM")) {
			setupAI();

			if (aiPlayer1 != null) {
				board.replaceIceCream(aiPlayer1);
			}
			if (aiPlayer2 != null) {
				board.replaceIceCream2(aiPlayer2);
			}
		}

		board.getIceCream().setBoard(board);

		if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
			board.getIceCream2().setBoard(board);
		}

		for (Enemy e : board.getEnemies()) {
			e.setBoard(board);
		}

		startTime = System.currentTimeMillis();
	}

	/**
	 * Configura las IAs según el modo de juego
	 */
	private void setupAI() {
		int player1X = board.getIceCream().getGridX();
		int player1Y = board.getIceCream().getGridY();
		int player2X = board.getIceCream2().getGridX();
		int player2Y = board.getIceCream2().getGridY();

		switch (gameMode) {
		case "PvM":
			// Jugador 1 es humano, Jugador 2 es IA
			aiPlayer1 = null;
			aiPlayer2 = createAI(player2X, player2Y, iceCreamColor2, ai2Profile);
			// === CORRECCIÓN: Asignar board inmediatamente ===
			if (aiPlayer2 != null) {
				aiPlayer2.setBoard(board);
				aiPlayer2.setGameMode(gameMode);
			}
			break;

		case "MvM":
			// Ambos jugadores son IAs
			aiPlayer1 = createAI(player1X, player1Y, iceCreamColor, ai1Profile);
			aiPlayer2 = createAI(player2X, player2Y, iceCreamColor2, ai2Profile);
			// === CORRECCIÓN: Asignar board inmediatamente ===
			if (aiPlayer1 != null) {
				aiPlayer1.setBoard(board);
				aiPlayer1.setGameMode(gameMode);
			}
			if (aiPlayer2 != null) {
				aiPlayer2.setBoard(board);
				aiPlayer2.setGameMode(gameMode);
			}
			break;

		case "Player":
		case "PvP":
		default:
			// Modos sin IA
			aiPlayer1 = null;
			aiPlayer2 = null;
			break;
		}
	}

	public void setAIProfiles(String profile1, String profile2) {
		this.ai1Profile = profile1;
		this.ai2Profile = profile2;
		setupAI();
	}

	// CREAR IA POR PERFIL
	private IceCreamAI createAI(int x, int y, Color color, String profile) {
		switch (profile.toLowerCase()) {
		case "hungry":
			return new HungryAI(x, y, color);
		case "fearful":
			return new FearfulAI(x, y, color);
		case "expert":
		default:
			return new ExpertAI(x, y, color);
		}
	}

	/**
	 * Configura el nivel especificado
	 */
	private void setupLevel(int level) {
		switch (level) {
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

		// CREAR IGLÚ CENTRAL (4x4 en el centro)
		int centerX = board.getCols() / 2;
		int centerY = board.getRows() / 2;

		// Agregar paredes del iglú (bloques permanentes)
		for (int i = centerY - 2; i <= centerY + 1; i++) {
			for (int j = centerX - 2; j <= centerX + 1; j++) {
				board.getGrid()[i][j] = 1;
				board.getBlocks().add(new Block(j, i));
			}
		}
		// Enemigos (aparecen desde el inicio)
		if (levelConfigs != null && levelConfigs.length > 0) {
			LevelConfiguration config = levelConfigs[0];
			addEnemyByType(config.getEnemyType(), 2, 2);
			addObstaclesByType(config.getObstacleType());
		} else {
			// Fallback por defecto
			board.addEnemy(new PatrollingEnemy(2, 2, 1, board.getCols() - 2, 1, board.getRows() - 2));
			board.addBonfire(new Bonfire(12, 8));
			board.addHotTile(new HotTile(14, 10));
		}

		fruitsToCollect = 16;
		spawnWave();
	}

	/**
	 * Configura el nivel 2 Enemigos: 1 Obstáculos:
	 */
	private void setupLevel2() {

		// CREAR IGLÚ CENTRAL (4x4)
		int centerX = board.getCols() / 2;
		int centerY = board.getRows() / 2;

		for (int i = centerY - 2; i <= centerY + 1; i++) {
			for (int j = centerX - 2; j <= centerX + 1; j++) {
				board.getGrid()[i][j] = 1;
				board.getBlocks().add(new Block(j, i));
			}
		}
		// Enemigos
		if (levelConfigs != null && levelConfigs.length > 1) {
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
	 * Configura el nivel 3:
	 */
	private void setupLevel3() {

		// CREAR IGLÚ CENTRAL (4x4)
		int centerX = board.getCols() / 2;
		int centerY = board.getRows() / 2;

		for (int i = centerY - 2; i <= centerY + 1; i++) {
			for (int j = centerX - 2; j <= centerX + 1; j++) {
				board.getGrid()[i][j] = 1;
				board.getBlocks().add(new Block(j, i));
			}
		}
		// Enemigos
		if (levelConfigs != null && levelConfigs.length > 2) {
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
		if (level == 1) {
			spawnWaveLevel1();
		} else if (level == 2) {
			spawnWaveLevel2();
		} else if (level == 3) {
			spawnWaveLevel3();
		}
	}

	/**
	 * Oleadas del Nivel 1: Según configuración del usuario
	 */
	private void spawnWaveLevel1() {
		if (currentWave >= 2)
			return;

		waveInProgress = true;
		LevelConfiguration config = levelConfigs[0];

		int centerX = board.getCols() / 2;
		int centerY = board.getRows() / 2;

		if (currentWave == 0) {
			// Primera oleada: 8 frutas en círculo ARRIBA y LADOS del iglú
			spawnFruitsByType(config.getFruit1Type(), new int[][] { { centerX - 3, centerY - 3 }, // Arriba izquierda
					{ centerX - 1, centerY - 3 }, // Arriba centro-izq
					{ centerX + 0, centerY - 3 }, // Arriba centro
					{ centerX + 2, centerY - 3 }, // Arriba derecha
					{ centerX - 4, centerY }, // Izquierda
					{ centerX + 3, centerY }, // Derecha
					{ centerX - 4, centerY + 1 }, // Izquierda abajo
					{ centerX + 3, centerY + 1 } // Derecha abajo
			});
		} else if (currentWave == 1) {
			// Segunda oleada: 8 frutas en círculo ABAJO del iglú
			spawnFruitsByType(config.getFruit2Type(), new int[][] { { centerX - 3, centerY + 3 }, // Abajo izquierda
					{ centerX - 1, centerY + 3 }, // Abajo centro-izq
					{ centerX + 0, centerY + 3 }, // Abajo centro
					{ centerX + 2, centerY + 3 }, // Abajo derecha
					{ centerX - 3, centerY + 4 }, // Muy abajo izq
					{ centerX, centerY + 4 }, // Muy abajo centro
					{ centerX + 2, centerY + 4 }, // Muy abajo der
					{ centerX - 1, centerY + 5 } // Última fila
			});
		}
		currentWave++;
	}

	/**
	 * Oleadas del Nivel 2: Según configuración del usuario
	 */
	private void spawnWaveLevel2() {
		if (currentWave >= 2)
			return;

		waveInProgress = true;
		LevelConfiguration config = levelConfigs[1];

		int centerX = board.getCols() / 2;
		int centerY = board.getRows() / 2;

		if (currentWave == 0) {
			// Primera oleada: 6 frutas arriba del iglú
			spawnFruitsByType(config.getFruit1Type(),
					new int[][] { { centerX - 3, centerY - 3 }, { centerX - 1, centerY - 3 },
							{ centerX + 0, centerY - 3 }, { centerX + 2, centerY - 3 }, { centerX - 2, centerY - 4 },
							{ centerX + 1, centerY - 4 } });
		} else if (currentWave == 1) {
			// Segunda oleada: 6 frutas abajo del iglú
			spawnFruitsByType(config.getFruit2Type(),
					new int[][] { { centerX - 3, centerY + 3 }, { centerX - 1, centerY + 3 },
							{ centerX + 0, centerY + 3 }, { centerX + 2, centerY + 3 }, { centerX - 2, centerY + 4 },
							{ centerX + 1, centerY + 4 } });
		}
		currentWave++;
	}

	/**
	 * Oleadas del Nivel 3: Según configuración del usuario
	 */
	private void spawnWaveLevel3() {
		if (currentWave >= 2)
			return;

		waveInProgress = true;
		LevelConfiguration config = levelConfigs[2];

		int centerX = board.getCols() / 2;
		int centerY = board.getRows() / 2;

		if (currentWave == 0) {
			// Primera oleada: 7 frutas arriba y lados
			spawnFruitsByType(config.getFruit1Type(),
					new int[][] { { centerX - 3, centerY - 3 }, { centerX, centerY - 3 }, { centerX + 2, centerY - 3 },
							{ centerX - 4, centerY }, { centerX + 3, centerY }, { centerX - 4, centerY + 1 },
							{ centerX + 3, centerY + 1 } });
		} else if (currentWave == 1) {
			// Segunda oleada: 7 frutas abajo
			spawnFruitsByType(config.getFruit2Type(),
					new int[][] { { centerX - 3, centerY + 3 }, { centerX - 1, centerY + 3 },
							{ centerX + 0, centerY + 3 }, { centerX + 2, centerY + 3 }, { centerX - 2, centerY + 4 },
							{ centerX + 1, centerY + 4 }, { centerX, centerY + 5 } });
		}
		currentWave++;
	}

	/**
	 * Genera frutas de un tipo específico en las posiciones dadas
	 */
	private void spawnFruitsByType(String type, int[][] positions) {
		for (int[] pos : positions) {
			Fruit fruit = createFruitByType(type, pos[0], pos[1]);
			if (fruit != null) {
				board.addFruit(fruit);
			}
		}
	}

	/**
	 * Crea una fruta según su tipo
	 */
	private Fruit createFruitByType(String type, int x, int y) {
		Fruit fruit = null;

		switch (type) {
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

		if (board.hasBlock(x, y)) {
			// Si la posición está bloqueada, buscar una posición libre cercana
			x = 2;
			y = 2;
			// Si (2,2) también está bloqueada, probar otras esquinas
			if (board.hasBlock(2, 2)) {
				x = board.getCols() - 3;
				y = 2;
			}
		}

		Enemy enemy = null;
		switch (type) {
		case "Troll":
			enemy = new PatrollingEnemy(x, y, 1, board.getCols() - 2, 1, board.getRows() - 2);
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

		if (enemy != null) {
			board.addEnemy(enemy);
		}
	}

	/**
	 * Agrega obstáculos según el tipo seleccionado
	 */
	private void addObstaclesByType(String type) {
		int centerY = board.getRows() / 2;
		switch (type) {
		case "Bonfire":
			board.addBonfire(new Bonfire(3, 3));
			board.addBonfire(new Bonfire(board.getCols() - 4, 3));
			break;
		case "HotTile":
			board.addHotTile(new HotTile(3, centerY));
			board.addHotTile(new HotTile(board.getCols() - 4, centerY));
			break;
		case "Both":
			board.addBonfire(new Bonfire(3, 3));
			board.addHotTile(new HotTile(board.getCols() - 4, centerY));
			break;
		}
	}

	/**
	 * Actualiza el estado del juego
	 */
	public void update() {
		if (!gameRunning || gamePaused)
			return;

		if (getTimeRemaining() <= 0) {
			resetLevel();
			return;
		}
		// Actualizar fogatas
		for (Bonfire bonfire : board.getBonfires()) {
			bonfire.update();

			if (bonfire.killsIceCream(board.getIceCream())) {
				resetToLevel1();
				return;
			}
			checkBonfireDamage(bonfire);

			// Verifica jugador 2
			if (gameMode.equals("PvP") || gameMode.equals("PvM")) {
				if (bonfire.killsIceCream(board.getIceCream2())) {
					resetToLevel1();
					return;
				}
			}
		}

		// Enemigos automáticamente
		enemyMoveCounter++;
		if (enemyMoveCounter >= enemyMoveDelay) {
			enemyMoveCounter = 0;

			// Solo mover enemigos cuando el contador alcanza el delay
			for (Enemy enemy : board.getEnemies()) {
				IceCream target = getClosestPlayer(enemy);
				enemy.walk(target);
			}
		}

		// Mover frutas
		for (Fruit fruit : board.getFruits()) {
			fruit.move();
		}

		updateAI();

		// Colisiones

		// Colisiones con frutas - Jugador 1
		checkFruitCollisions(board.getIceCream(), 1);

		// Colisiones con frutas - Jugador 2 (solo en PvP)
		if (gameMode.equals("PvP")) {
			checkFruitCollisions(board.getIceCream2(), 2);
		}

		// checkCactusCollisions();

		if (aiPlayer1 != null) {
			checkFruitCollisionsForAI(aiPlayer1, 1);
		}
		if (aiPlayer2 != null) {
			checkFruitCollisionsForAI(aiPlayer2, 2);
		}

		// Verifica si se completó la oleada actual
		if (waveInProgress && board.getFruits().isEmpty() && currentWave < 2) {
			waveInProgress = false;
			spawnWave(); // Genera la siguiente oleada
		}

		checkEnemyCollisions();

		checkCactusDamage();

		if (fruitsCollected >= fruitsToCollect) {
			gameRunning = false;
			// didWin = true;
		}

		
	}

	private void updateAI() {
		aiDecisionCounter++;
		if (aiDecisionCounter >= aiDecisionDelay) {
			aiDecisionCounter = 0;

			if (aiPlayer1 != null) {
				if (aiPlayer1.isTrapped()) {
					aiPlayer1.attemptBreakIce();
				} else {
					aiPlayer1.makeDecision();
				}
			}

			if (aiPlayer2 != null) {
				if (aiPlayer2.isTrapped()) {
					aiPlayer2.attemptBreakIce();
				} else {
					aiPlayer2.makeDecision();
				}
			}
		}
	}

	private void checkBonfireDamage(Bonfire bonfire) {
		if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
			if (bonfire.killsIceCream(board.getIceCream2())) {
				resetToLevel1();
				return;
			}
		}

		// Verificar IAs
		if (aiPlayer1 != null && bonfire.killsIceCream(aiPlayer1)) {
			resetToLevel1();
			return;
		}
		if (aiPlayer2 != null && bonfire.killsIceCream(aiPlayer2)) {
			resetToLevel1();
			return;
		}
	}

	private void checkFruitCollisionsForAI(IceCreamAI ai, int playerNumber) {
		for (int i = board.getFruits().size() - 1; i >= 0; i--) {
			Fruit f = board.getFruits().get(i);
			if (f.isVisibleFruit() && ai.getGridX() == f.getGridX() && ai.getGridY() == f.getGridY()) {

				if (f instanceof Cactus) {
					Cactus cactus = (Cactus) f;
					if (cactus.hasSpikes()) {
						continue; // No recolectar si tiene púas
					}
				}

				int points = f.getPoints();

				if (gameMode.equals("PvM") || gameMode.equals("MvM")) {
					if (playerNumber == 1) {
						scorePlayer1 += points;
					} else {
						scorePlayer2 += points;
					}
				}

				board.getFruits().remove(i);
				fruitsCollected++;
			}
		}
	}

	private void checkEnemyCollisions() {
		for (Enemy enemy : board.getEnemies()) {
			// Jugador 1
			if (board.getIceCream().getGridX() == enemy.getGridX()
					&& board.getIceCream().getGridY() == enemy.getGridY()) {
				resetToLevel1();
				return;
			}

			// Jugador 2
			if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
				if (board.getIceCream2().getGridX() == enemy.getGridX()
						&& board.getIceCream2().getGridY() == enemy.getGridY()) {
					resetToLevel1();
					return;
				}
			}

			// VERIFICAR IAs
			if (aiPlayer1 != null && aiPlayer1.getGridX() == enemy.getGridX()
					&& aiPlayer1.getGridY() == enemy.getGridY()) {
				resetToLevel1();
				return;
			}

			if (aiPlayer2 != null && aiPlayer2.getGridX() == enemy.getGridX()
					&& aiPlayer2.getGridY() == enemy.getGridY()) {
				resetToLevel1();
				return;
			}
		}
	}

	private void checkCactusDamage() {
		for (Fruit fruit : board.getFruits()) {
			if (fruit instanceof Cactus) {
				Cactus cactus = (Cactus) fruit;
				if (cactus.hasSpikes()) {
					// Jugador 1
					if (cactus.hurtsIceCream(board.getIceCream())) {
						resetToLevel1();
						return;
					}

					// Jugador 2
					if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
						if (cactus.hurtsIceCream(board.getIceCream2())) {
							resetToLevel1();
							return;
						}
					}

					// VERIFICAR IAs
					if (aiPlayer1 != null && cactus.hurtsIceCream(aiPlayer1)) {
						resetToLevel1();
						return;
					}
					if (aiPlayer2 != null && cactus.hurtsIceCream(aiPlayer2)) {
						resetToLevel1();
						return;
					}
				}
			}
		}
	}

	// Método para ajustar la velocidad globalmente
	public void setEnemyMoveDelay(int delay) {
		this.enemyMoveDelay = delay;
	}

	/**
	 * Verifica colisiones con frutas para un jugador específico
	 * 
	 * @param player       El jugador (IceCream)
	 * @param playerNumber 1 o 2
	 */
	private void checkFruitCollisions(IceCream player, int playerNumber) {
		for (int i = board.getFruits().size() - 1; i >= 0; i--) {
			Fruit f = board.getFruits().get(i);
			if (f.isVisibleFruit() && player.getGridX() == f.getGridX() && // ← Usa el jugador pasado como parámetro
					player.getGridY() == f.getGridY()) {

				if (f instanceof Cactus) {
					Cactus cactus = (Cactus) f;
					if (cactus.hasSpikes()) {
						continue; // No recolectar si tiene púas
					}
				}

				// Calcular puntos según el tipo de fruta
				int points = f.getPoints();

				// Asignar puntos según el modo de juego
				if (gameMode.equals("PvP")) {
					if (playerNumber == 1) {
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
	 * 
	 * @param direction
	 */
	public void movePlayer(int direction) {
		// currentPlayerDirection = direction;
		board.getIceCream().move(direction);

	}

	/**
	 * Mueve al jugador 2
	 * 
	 * @param direction
	 */
	public void movePlayer2(int direction) { // ← AQUÍ
		// currentPlayer2Direction = direction;
		if (gameMode.equals("PvP")) {
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

		switch (direction) {
		case IceCream.UP:
			targetY--;
			break; // Arriba
		case IceCream.RIGHT:
			targetX++;
			break; // Derecha
		case IceCream.DOWN:
			targetY++;
			break; // Abajo
		case IceCream.LEFT:
			targetX--;
			break; // Izquierda
		}
		if (board.hasBlock(targetX, targetY)) {
			breakIce();
		} else {
			board.getIceCream().shootIce();
		}

	}

	/**
	 * Realiza un disparo de hielo en la direccion actual - Jugador 2
	 */
	public void shootIce2() { // ← AQUÍ
		if (!gameMode.equals("PvP"))
			return;

		IceCream iceCream2 = board.getIceCream2();
		int direction = iceCream2.getDirection();
		int targetX = iceCream2.getGridX();
		int targetY = iceCream2.getGridY();

		switch (direction) {
		case IceCream.UP:
			targetY--;
			break; // Arriba
		case IceCream.RIGHT:
			targetX++;
			break; // Derecha
		case IceCream.DOWN:
			targetY++;
			break; // Abajo
		case IceCream.LEFT:
			targetX--;
			break; // Izquierda
		}

		if (board.hasBlock(targetX, targetY)) {
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

		setupAI();

		// Reemplazar jugadores con IAs si existen
		if (aiPlayer1 != null) {
			board.replaceIceCream(aiPlayer1);
		} else {
			board.getIceCream().setBoard(board);
		}

		if (aiPlayer2 != null) {
			board.replaceIceCream2(aiPlayer2);
		} else if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
			board.getIceCream2().setBoard(board);
		}

		for (Enemy e : board.getEnemies()) {
			e.setBoard(board);
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

		setupAI();

		// Reemplazar jugadores con IAs si existen
		if (aiPlayer1 != null) {
			board.replaceIceCream(aiPlayer1);
		} else {
			board.getIceCream().setBoard(board);
		}

		if (aiPlayer2 != null) {
			board.replaceIceCream2(aiPlayer2);
		} else if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
			board.getIceCream2().setBoard(board);
		}

		for (Enemy e : board.getEnemies()) {
			e.setBoard(board);
		}
	}

	/**
	 * Avanza al siguiente nivel
	 */
	public void nextLevel() {
		if (level < 3) {
			level++;
			fruitsCollected = 0;
			// MANTENER EL SCORE ACUMULADO
			currentWave = 0;
			waveInProgress = false;
			startTime = System.currentTimeMillis();
			gameRunning = true;
			gamePaused = false;
			// didWin = false;
			board = new Board();
			setupLevel(level);

			setupAI();

			// Reemplazar jugadores con IAs si existen
			if (aiPlayer1 != null) {
				board.replaceIceCream(aiPlayer1);
			} else {
				board.getIceCream().setBoard(board);
			}

			if (aiPlayer2 != null) {
				board.replaceIceCream2(aiPlayer2);
			} else if (gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) {
				board.getIceCream2().setBoard(board);
			}

			for (Enemy e : board.getEnemies()) {
				e.setBoard(board);
			}
		}
	}

	/**
	 * Carga el estado del juego desde un GameState
	 */
	public void loadFromState(GameState state) throws BadDopoCreamExceptions {
		if (state == null) {
			throw new BadDopoCreamExceptions(BadDopoCreamExceptions.LOADGAME_ERROR);
		}
		this.level = state.getLevel();
		this.gameMode = state.getGameMode();
		this.score = state.getScore();
		this.scorePlayer1 = state.getScorePlayer1();
		this.scorePlayer2 = state.getScorePlayer2();
		this.fruitsCollected = state.getFruitsCollected();
		this.currentWave = state.getCurrentWave();
		// this.waveInProgress = false;

		// Calcular tiempo restante
		this.startTime = System.currentTimeMillis() - state.getElapsedTime();

		// Recrear el tablero
		board = new Board();
		setupLevel(level);

		int[][] savedGrid = state.getGrid();
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {
				board.getGrid()[i][j] = savedGrid[i][j];
			}
		}
		board.getBlocks().clear();
		board.getBlocks().addAll(state.getBlocks());

		// Restaurar posiciones de helados
		board.getIceCream().setPosition(state.getIceCream1X(), state.getIceCream1Y());
		board.getIceCream().setBoard(board);

		if (gameMode.equals("PvP") || gameMode.equals("PvM")) {
			board.getIceCream2().setPosition(state.getIceCream2X(), state.getIceCream2Y());
			board.getIceCream2().setBoard(board);
		}

		// Limpiar frutas actuales y cargar las guardadas
		board.getFruits().clear();
		for (Fruit fruit : state.getFruits()) {
			board.getFruits().add(fruit);
			// Reconectar referencias transient
			if (fruit instanceof Cherry) {
				((Cherry) fruit).setBoard(board);
			} else if (fruit instanceof Pineapple) {
				((Pineapple) fruit).setBoard(board);
				((Pineapple) fruit).setIceCream(board.getIceCream());
			}
		}

		// Cargar enemigos
		board.getEnemies().clear();
		for (Enemy enemy : state.getEnemies()) {
			board.getEnemies().add(enemy);
			enemy.setBoard(board);
		}

		// Cargar fogatas
		board.getBonfires().clear();
		board.getBonfires().addAll(state.getBonfires());

		// Restaurar baldosas calientes directamente
		board.getHotTiles().clear();
		board.getHotTiles().addAll(state.getHotTiles());

		gameRunning = true;
		gamePaused = false;
	}

	/**
	 * Obtiene el jugador más cercano al enemigo (para modos multijugador)
	 */
	private IceCream getClosestPlayer(Enemy enemy) {
		if (gameMode.equals("Player")) {
			return board.getIceCream();
		}

		IceCream player1 = board.getIceCream();
		IceCream player2 = board.getIceCream2();

		if (aiPlayer1 != null) {
			player1 = aiPlayer1;
		}
		if (aiPlayer2 != null) {
			player2 = aiPlayer2;
		}

		int dist1 = Math.abs(enemy.getGridX() - player1.getGridX()) + Math.abs(enemy.getGridY() - player1.getGridY());
		int dist2 = Math.abs(enemy.getGridX() - player2.getGridX()) + Math.abs(enemy.getGridY() - player2.getGridY());

		return (dist1 <= dist2) ? player1 : player2;
	}

	/**
	 * Obtiene el perfil de IA del jugador 1
	 */
	public String getAI1Profile() {
		return ai1Profile;
	}

	/**
	 * Obtiene el perfil de IA del jugador 2
	 */
	public String getAI2Profile() {
		return ai2Profile;
	}

	public IceCreamAI getAIPlayer1() {
		return aiPlayer1;
	}

	public IceCreamAI getAIPlayer2() {
		return aiPlayer2;
	}

	public int getFruitsCollected() {
		return fruitsCollected;
	}

	public int getScore() {
		return score;
	}

	public int getTimeRemaining() {
		long elapsed = (System.currentTimeMillis() - startTime) / 1000;
		return (int) (timeLimit - elapsed);
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
