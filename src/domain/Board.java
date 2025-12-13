package domain;
import java.util.*;
import java.awt.Color;

public class Board {
	private int rows = 16;
	private int cols = 18;
	private int cellSize = 30;
	private int width;
	private int height;
	
	private int[][] grid;
	private ArrayList<Block> blocks;
	private ArrayList<Enemy> enemies;
	private ArrayList<Fruit> fruits;
	private ArrayList<Bonfire> bonfires;
	private ArrayList<HotTile> hotTiles;
	private IceCream iceCream;
	private IceCream iceCream2;
	private Color color = new Color(200, 220, 255);
	
	public Board() {
		this.width = cols * cellSize;
		this.height = rows * cellSize;
		this.grid = new int[rows][cols];
		this.blocks = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.fruits = new ArrayList<>();
		this.bonfires = new ArrayList<>();
		this.hotTiles = new ArrayList<>();
	}
	
	private void initializeWalls() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = 0;
			}
		}
		
		for(int i = 0; i < rows; i++) {
			grid[i][0] = 1;
			grid[i][cols-1] = 1;
			blocks.add(new Block(0, i));
			blocks.add(new Block(cols-1, i));
		}
		
		for(int j = 0; j < cols; j++) {
			grid[0][j] = 1;
			grid[rows-1][j] = 1;
			blocks.add(new Block(j, 0));
			blocks.add(new Block(j, rows-1));
		}
	}
	
	/**
	 * Inicializa los jugadores
	 */
	private void initializePlayers(Color iceCreamColor, Color iceCream2Color) {
		int heladoX = 7;
		int heladoY = 11;
		grid[heladoY][heladoX] = 4;
		iceCream = new IceCream(heladoX, heladoY, iceCreamColor);
		
		int helado2X = 10;
		int helado2Y = 13;
		grid[helado2Y][helado2X] = 4;
		iceCream2 = new IceCream(helado2X, helado2Y, iceCream2Color);
	}
	
	/**
	 * Nivel 1: Diseño simple con pasillos y esquinas
	 * Inspirado en el nivel 1 original
	 */
	public void level1(Color iceCreamColor, Color iceCream2Color, boolean isPvP) {
		initializeWalls();
		
		int[][] blockPositions = {
			{2,2},{3,2},{4,2},
			{2,3},{2,4},

			{13,2},{14,2},{15,2},
			{15,3},{15,4},

			{3,7},{3,8},{3,9},
			{4,7},{4,8},{4,9},

			{13,7},{13,8},{13,9},
			{14,7},{14,8},{14,9},

			{2,11},{2,12},{2,13},
			{3,13},{4,13},

			{15,11},{15,12},{15,13},
			{13,13},{14,13},
			
			{7,5},{10,5},
			{7,10},{10,10}
		};
		
		for(int[] pos : blockPositions) {
			grid[pos[1]][pos[0]] = 1;
			blocks.add(new Block(pos[0], pos[1]));
		}
		
		initializePlayers(iceCreamColor, iceCream2Color);
	}
	
	/**
	 * Nivel 2: Laberinto con corredores
	 * Más espacios para movimiento estratégico
	 */
	public void level2(Color iceCreamColor, Color iceCream2Color, boolean isPvP) {
		initializeWalls();
		
		int[][] blockPositions = {
			{3,2},{3,4},{3,5},
			{6,4},{6,6},{6,7},
			

			{14,2},{14,3},{14,4},{14,5},
			{11,4},{11,6},{11,7},
			

			{5,3},{7,3},{8,3},{9,3},{10,3},{12,3},

			{5,8},{6,8},
			{11,8},{12,8},

			{3,10},{3,11},{3,12},{3,13},
			{14,10},{14,11},{14,12},

			{7,11},{10,11},

			{8,6},{9,6},
			{8,9},{9,9}
		};
		
		for(int[] pos : blockPositions) {
			grid[pos[1]][pos[0]] = 1;
			blocks.add(new Block(pos[0], pos[1]));
		}
		
		initializePlayers(iceCreamColor, iceCream2Color);
	}
	
	/**
	 * Nivel 3: Diseño complejo tipo arena
	 * Muchos espacios pero estratégicos
	 */
	public void level3(Color iceCreamColor, Color iceCream2Color, boolean isPvP) {
		initializeWalls();
		
		int[][] blockPositions = {

			{2,2},{3,2},{2,3},
			{14,2},{15,2},{15,3},
			{2,12},{3,12},{2,13},{3,13},
			{14,12},{15,12},{14,13},{15,13},

			{8,2},{9,2},
			{8,3},{9,3},
			{7,3},{10,3},
			
			{4,5},{5,5},
			{4,6},{5,6},
			{4,9},
			{4,10},{5,10},

			{13,5},
			{12,6},{13,6},
			{13,9},
			{12,10},{13,10},
			
			{6,7},{11,7},
			{6,8},{11,8},

			{8,12},{9,12},
		};
		
		for(int[] pos : blockPositions) {
			grid[pos[1]][pos[0]] = 1;
			blocks.add(new Block(pos[0], pos[1]));
		}
		
		initializePlayers(iceCreamColor, iceCream2Color);
	}
	
	/**
	 * Verifica si una posición es segura para colocar frutas
	 * (no hay bloques, paredes, ni está en el área del iglú)
	 */
	public boolean isSafeForFruit(int x, int y) {
		// Verificar límites
		if(x <= 0 || x >= cols-1 || y <= 0 || y >= rows-1) return false;
		
		// Verificar si hay bloque
		if(grid[y][x] == 1) return false;
		
		// Verificar área del iglú (centro 4x4)
		int centerX = cols / 2;
		int centerY = rows / 2;
		if(x >= centerX - 2 && x < centerX + 2 && 
		   y >= centerY - 2 && y < centerY + 2) {
			return false;
		}
		
		// Verificar posición inicial jugador 1
		if(x >= 1 && x <= 3 && y >= 1 && y <= 3) return false;
		
		// Verificar posición inicial jugador 2
		if(x >= cols-4 && x <= cols-2 && y >= rows-4 && y <= rows-2) return false;
		
		return true;
	}
	
	public void addFruit(Fruit fruit) {
		fruits.add(fruit);
		grid[fruit.getGridY()][fruit.getGridX()] = 2;
	}
	
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
		enemy.setBoard(this);
		
		if(grid[enemy.getGridY()][enemy.getGridX()] != 1) {
			grid[enemy.getGridY()][enemy.getGridX()] = 2;
		}
	}
	
	public boolean canMoveTo(int x, int y) {
		if(x < 0 || x >= cols || y < 0 || y >= rows) return false;
		return grid[y][x] != 1;
	}
	
	public boolean hasBlock(int x, int y) {
		if(x < 0 || x >= cols || y < 0 || y >= rows) return false;
		return grid[y][x] == 1;
	}
	
	public void setBlock(int x, int y) {
		if(x >= 0 && x < cols && y >= 0 && y < rows && grid[y][x] != 1) {
			for(HotTile tile : hotTiles) {
				if(tile.meltsBlock(x, y)) {
					return;
				}
			}
			
			grid[y][x] = 1;
			blocks.add(new Block(x, y));
			
			for(Bonfire bonfire : bonfires) {
				if(bonfire.getGridX() == x && bonfire.getGridY() == y) {
					bonfire.extinguish();
				}
			}
		}
	}
	
	public void removeBlock(int x, int y) {
		for(int i = blocks.size() - 1; i >= 0; i--) {
			Block b = blocks.get(i);
			if(b.getGridX() == x && b.getGridY() == y && grid[y][x] == 1) {
				grid[y][x] = 0;
				blocks.remove(i);
				break;
			}
		}
	}
	
	public void addBonfire(Bonfire bonfire) {
		bonfires.add(bonfire);
	}
	
	public void addHotTile(HotTile tile) {
		hotTiles.add(tile);
	}
	
	public void replaceIceCream(IceCream newIceCream) {
		int oldX = iceCream.getGridX();
		int oldY = iceCream.getGridY();
		this.iceCream = newIceCream;
		iceCream.setPosition(oldX, oldY);
		iceCream.setBoard(this);
	}
	
	public void replaceIceCream2(IceCream newIceCream) {
		int oldX = iceCream2.getGridX();
		int oldY = iceCream2.getGridY();
		this.iceCream2 = newIceCream;
		iceCream2.setPosition(oldX, oldY);
		iceCream2.setBoard(this);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public IceCream getIceCream() {
		return iceCream;
	}
	
	public IceCream getIceCream2() {
		return iceCream2;
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public ArrayList<Fruit> getFruits() {
		return fruits;
	}
	
	public ArrayList<Bonfire> getBonfires() {
		return bonfires;
	}
	
	public ArrayList<HotTile> getHotTiles() {
		return hotTiles;
	}
	
	public Color getColor() {
		return color;
	}
}