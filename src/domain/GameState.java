package domain;

import java.io.Serializable;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Representa el estado completo de una partida
 * para poder guardarla y cargarla
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Información del juego
    private int level;
    private String gameMode;
    private int score;
    private int scorePlayer1;
    private int scorePlayer2;
    private int fruitsCollected;
    private long elapsedTime; // Tiempo transcurrido en milisegundos
    
    // Colores de los helados
    private Color iceCreamColor;
    private Color iceCreamColor2;
    
    // Posiciones de los helados
    private int iceCream1X;
    private int iceCream1Y;
    private int iceCream2X;
    private int iceCream2Y;
    
    private int[][] grid;
    
    // Estados de las frutas
    private ArrayList<Fruit> fruits;
    
    // Estados de los enemigos
    private ArrayList<Enemy> enemies;
    
    // Estados de los bloques
    private ArrayList<Block> blocks;
    
    // Estados de las fogatas
    private ArrayList<Bonfire> bonfires;
    private ArrayList<HotTile> hotTiles;
    
    // Información de oleadas
    private int currentWave;
    
    private LevelConfiguration[] levelConfigs;
    
    /**
     * Constructor vacío
     */
    public GameState() {
        fruits = new ArrayList<>();
        enemies = new ArrayList<>();
        blocks = new ArrayList<>();
        bonfires = new ArrayList<>();
        hotTiles = new ArrayList<>();
    }
    
    /**
     * Crea un GameState desde el estado actual del juego
     */
    public static GameState fromGameController(GameController controller) {
        GameState state = new GameState();
        
        // Información básica
        state.level = controller.getLevel();
        state.gameMode = controller.getGameMode();
        state.score = controller.getScore();
        state.scorePlayer1 = controller.getScorePlayer1();
        state.scorePlayer2 = controller.getScorePlayer2();
        state.fruitsCollected = controller.getFruitsCollected();
        state.elapsedTime = System.currentTimeMillis() - controller.getStartTime();
        
        Board board = controller.getBoard();
        
        // Colores
        state.iceCreamColor = board.getIceCream().getColor();
        state.iceCreamColor2 = board.getIceCream2().getColor();
        
        // Posiciones helados
        state.iceCream1X = board.getIceCream().getGridX();
        state.iceCream1Y = board.getIceCream().getGridY();
        state.iceCream2X = board.getIceCream2().getGridX();
        state.iceCream2Y = board.getIceCream2().getGridY();
        
     // Copiar grid completo
        state.grid = new int[board.getRows()][board.getCols()];
        for(int i = 0; i < board.getRows(); i++) {
            for(int j = 0; j < board.getCols(); j++) {
                state.grid[i][j] = board.getGrid()[i][j];
            }
        }
        
        state.blocks = new ArrayList<>(board.getBlocks());
        state.fruits = new ArrayList<>(board.getFruits());
        state.enemies = new ArrayList<>(board.getEnemies());
        state.bonfires = new ArrayList<>(board.getBonfires());
        state.hotTiles = new ArrayList<>(board.getHotTiles());
        
        state.currentWave = controller.getCurrentWave();
        
        state.levelConfigs = controller.getLevelConfigs();
        
        return state;
    }
    
    // Getters y Setters
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
    public String getGameMode() { return gameMode; }
    public void setGameMode(String gameMode) { this.gameMode = gameMode; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getScorePlayer1() { return scorePlayer1; }
    public void setScorePlayer1(int score) { this.scorePlayer1 = score; }
    
    public int getScorePlayer2() { return scorePlayer2; }
    public void setScorePlayer2(int score) { this.scorePlayer2 = score; }
    
    public int getFruitsCollected() { return fruitsCollected; }
    public void setFruitsCollected(int fruits) { this.fruitsCollected = fruits; }
    
    public long getElapsedTime() { return elapsedTime; }
    public void setElapsedTime(long time) { this.elapsedTime = time; }
    
    public Color getIceCreamColor() { return iceCreamColor; }
    public void setIceCreamColor(Color color) { this.iceCreamColor = color; }
    
    public Color getIceCreamColor2() { return iceCreamColor2; }
    public void setIceCreamColor2(Color color) { this.iceCreamColor2 = color; }
    
    public int getIceCream1X() { return iceCream1X; }
    public void setIceCream1X(int x) { this.iceCream1X = x; }
    
    public int getIceCream1Y() { return iceCream1Y; }
    public void setIceCream1Y(int y) { this.iceCream1Y = y; }
    
    public int getIceCream2X() { return iceCream2X; }
    public void setIceCream2X(int x) { this.iceCream2X = x; }
    
    public int getIceCream2Y() { return iceCream2Y; }
    public void setIceCream2Y(int y) { this.iceCream2Y = y; }
    
    public int[][] getGrid() { return grid; }
    public ArrayList<Block> getBlocks() { return blocks; }
    public ArrayList<Fruit> getFruits() { return fruits; }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Bonfire> getBonfires() { return bonfires; }
    public ArrayList<HotTile> getHotTiles() { return hotTiles; }
    
    
    public int getCurrentWave() { return currentWave; }
    public void setCurrentWave(int wave) { this.currentWave = wave; }
    
    
    public LevelConfiguration[] getLevelConfigs() { 
        return levelConfigs; 
    }
    
    public void setLevelConfigs(LevelConfiguration[] configs) { 
        this.levelConfigs = configs; 
    }
    

}