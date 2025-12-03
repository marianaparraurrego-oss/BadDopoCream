package domain;
import java.util.*;
import java.awt.Color;
/**
 * Representa el yablero de juego que incluye los bloques, enemigos
 * frutas y el personaje
 */
public class Board {
	private int rows = 16;
	private int cols = 18;
	private int cellSize =30 ;
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
	private Color color = new Color(200,220,255);
	
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
	
	/**
	 * Genera el nivel1 con paredes, bloques
	 * y enemigos
	 * @param iceCreamColor color del helado
	 */
	public void level1(Color iceCreamColor, Color iceCream2Color, boolean isPvP) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = 0;
			}
		}
		//paredes 
		for (int i = 0; i < rows; i++) {
			grid[i][0] = 1;
			grid[i][cols-1] = 1;
			blocks.add(new Block(0,i));
			blocks.add(new Block(cols-1, i));
		}
		for (int j = 0; j < cols; j++) {
			grid[0][j] = 1;
			grid[rows-1][j] = 1;
			blocks.add(new Block(j,0));
			blocks.add(new Block(j, rows-1));
		}
		
		int[][] blockPositions = {
				{3,3},{4,3},{5,3},{6,3},
				{3,4},{3,5},{3,6},
				{8,3},{8,4},{8,5},
				{5,5},{6,5},{5,6},{6,6},
				{10,4},{10,5},{10,6}
		};
		
		for(int[] pos: blockPositions) {
			grid[pos[1]][pos[0]] = 1;
			blocks.add(new Block(pos[0], pos[1]));
		}
		
		int heladoX = 7;
		int heladoY = 10;
		grid[heladoY][heladoX] = 4 ;
		iceCream = new IceCream(heladoX, heladoY, iceCreamColor);
		
		int helado2X = 10;
		int helado2Y = 13;
		grid[helado2Y][helado2X] = 4 ;
		iceCream2 = new IceCream(helado2X, helado2Y, iceCream2Color);
		
		
		PatrollingEnemy e1 = new PatrollingEnemy(2,2,1,cols-2,1,rows-2);
		addEnemy(e1);

		PatrollingEnemy e2 = new PatrollingEnemy(10,2,1,cols-2,1,rows-2);
		addEnemy(e2);
		
	}
	
	/**
	 * Agrega una fruta al tablero
	 */
	public void addFruit(Fruit fruit) {
		fruits.add(fruit);
		grid[fruit.getGridY()][fruit.getGridX()] = 2;
	}
	
	/**
	 * Agrega un enemigo al tablero
	 */
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
		enemy.setBoard(this);
		grid[enemy.getGridY()][enemy.getGridX()] = 3;
	}
	
	/**
	 * Verifica si se puede mover hacia
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canMoveTo(int x, int y) {
		if(x < 0 || x >= cols || y < 0 || y >= rows) return false;
		return grid[y][x] != 1; //No puede haber bloques
	}
	
	
	/**
	 * Revisa si hay bloques
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasBlock(int x, int y) {
		if(x < 0 || x >= cols || y < 0 || y >= rows) return false;
		return grid[y][x] == 1;
	}
	/**
	 * Coloca bloques
	 * @param x
	 * @param y
	 */
	public void setBlock(int x, int y) {
		if(x >= 0 && x < cols && y >= 0 && y < rows && grid[y][x] != 1) {
			// Verifica si hay una baldosa caliente
			for(HotTile tile : hotTiles) {
				if(tile.meltsBlock(x, y)) {
					return; // No crea el bloque, se derrite
				}
			}
			
			grid[y][x] = 1;
			blocks.add(new Block(x,y));
			
			// Si hay una fogata, la apaga
			for(Bonfire bonfire : bonfires) {
				if(bonfire.getGridX() == x && bonfire.getGridY() == y) {
					bonfire.extinguish();
				}
			}
		}
	}
	/**
	 * Quita bloques
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Agrega una fogata al tablero
	 */
	public void addBonfire(Bonfire bonfire) {
		bonfires.add(bonfire);
	}
	
	/**
	 * Agrega una baldosa caliente al tablero
	 */
	public void addHotTile(HotTile tile) {
		hotTiles.add(tile);
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

