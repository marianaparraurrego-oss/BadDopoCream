package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel {
	private GameController gameController;
	private MainWindow mainWindow; 
	private Set<Integer> keysPressed;
	private long lastSpacePress = 0;
	private long lastShiftPress = 0;
	private long spaceDelay = 200;
	
	private int enemyMoveCounter = 0;
	private int enemyMoveDelay = 40;
	
	public GamePanel(GameController gameController, MainWindow mainWindow) {
		this.gameController = gameController;
		this.mainWindow = mainWindow;
		this.keysPressed = new HashSet<>();
		
		prepareElements();
		prepareActions();
		startGameLoop();
	}
	
	private void prepareElements() {
		setFocusable(true);
		setBackground(new Color(200, 220, 255));
	}
	
	private void prepareActions() {
		addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				keysPressed.add(e.getKeyCode());
				 //Pause
				if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					gameController.pauseGame();
				}
				
				// Guardar partida (F5)
				if(e.getKeyCode() == KeyEvent.VK_F5) {
					gameController.pauseGame(); 
					mainWindow.showSavePanel();
				}
				//shoot ice jugador 1
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					long now = System.currentTimeMillis();
					if(now - lastSpacePress > spaceDelay) {
						gameController.shootIce();
						lastSpacePress = now;
					}
				}
				
				// Shoot ice - Jugador 2 (SHIFT)
				if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
					long now = System.currentTimeMillis();
					if(now - lastShiftPress > spaceDelay) {
						gameController.shootIce2();
						lastShiftPress = now;
					}
				}
				
				// Siguiente nivel (solo cuando ganó)
				if(!gameController.isGameRunning() && gameController.didWin()) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						int currentLevel = gameController.getLevel();
						if(currentLevel < 3) {
							gameController.nextLevel();
				    
				            repaint();
				            requestFocus();
						} else {
							mainWindow.returnToMenu();
						}
					}
				}
			
				if(!gameController.isGameRunning() && !gameController.didWin()) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						gameController.resetToLevel1();
					}
				}
			}
		
			@Override
			public void keyReleased(KeyEvent e) {
				keysPressed.remove(e.getKeyCode());
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!gameController.isGameRunning() && gameController.didWin()) {
					Board board = gameController.getBoard();
					int buttonY = board.getHeight()/2 + 60;
					
					// Botón "Siguiente Nivel"
					if(gameController.getLevel() < 3) {
						if(e.getX() >= 80 && e.getX() <= 280 && 
						   e.getY() >= buttonY && e.getY() <= buttonY + 40) {
							gameController.nextLevel();
							
							revalidate();
				            repaint();
				            requestFocus();
						}
					}
					
					// Botón "Menú"
					int menuButtonX = gameController.getLevel() < 3 ? 340 : 150;
					if(e.getX() >= menuButtonX && e.getX() <= menuButtonX + 200 && 
					   e.getY() >= buttonY && e.getY() <= buttonY + 40) {
						mainWindow.returnToMenu();
					}
				}
			}
		});
	}
	
	private void startGameLoop() {
			Timer timer = new Timer(100, e -> {
				handleInput();
				
				enemyMoveCounter++;
				boolean shouldMoveEnemies = (enemyMoveCounter >= enemyMoveDelay);
				if(shouldMoveEnemies) {
					enemyMoveCounter = 0;
				}
				
				gameController.update();
				repaint();
			});
			timer.start();
	}
	
	private void handleInput() {
		 // SOLO permitir movimiento humano en modos con jugador humano
	    if (gameController.getGameMode().equals("Player") || 
	        gameController.getGameMode().equals("PvP") ||
	        gameController.getGameMode().equals("PvM")) {
	        
	        //jugador 1 (solo en modos donde hay jugador humano)
	        if(gameController.getGameMode().equals("Player") || 
	           gameController.getGameMode().equals("PvP") ||
	           gameController.getGameMode().equals("PvM")) {
		//jugador 1
				if(keysPressed.contains(KeyEvent.VK_UP) ) {
					gameController.movePlayer(0);
				}
				
				if(keysPressed.contains(KeyEvent.VK_RIGHT) ) {
					gameController.movePlayer(1);
				}
				
				if(keysPressed.contains(KeyEvent.VK_DOWN)) {
					gameController.movePlayer(2);
				}
				
				if(keysPressed.contains(KeyEvent.VK_LEFT) ) {
					gameController.movePlayer(3);
				}
	        }
			
			
			//Jugador2
			if(keysPressed.contains(KeyEvent.VK_W)) {
				gameController.movePlayer2(0);
			}
			
			if(keysPressed.contains(KeyEvent.VK_D)) {
				gameController.movePlayer2(1);
			}
			
			if(keysPressed.contains(KeyEvent.VK_S)) {
				gameController.movePlayer2(2);
			}
			
			if(keysPressed.contains(KeyEvent.VK_A)) {
				gameController.movePlayer2(3);
			}
	    }
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		Board board = gameController.getBoard();
		int cellSize = board.getCellSize();
		
		//Background
		g2d.setColor(board.getColor());
		g2d.fillRect(0,0, board.getWidth(), board.getHeight());
		
		//grid
		g2d.setColor(new Color(220, 230, 245));
		for( int i = 0; i <= board.getRows(); i++) {
			g2d.drawLine(0, i*cellSize, board.getWidth(), i*cellSize);
		}
		
		for( int j = 0; j <= board.getCols(); j++) {
			g2d.drawLine( j*cellSize, 0 , j*cellSize, board.getHeight());
		}
		
		//Blocks
		drawBlocks(g2d, board, cellSize);
		
		//HotTiles
		drawHotTiles(g2d, board, cellSize);
		
		// Bonfires
		drawBonfires(g2d, board, cellSize);
		
		//Fruits
		drawFruits(g2d, board, cellSize);
		
		//Ice cream
		drawIceCreams(g2d, board, cellSize);
		
		//Enemies
		drawEnemies(g2d, board, cellSize);
		
		// 	Hud
		drawHUD(g2d, board);
		
		//draw pause
		if(gameController.isGamePaused()) {
			drawPause(g2d);
		}
		
		//draw GameOver
		if(!gameController.isGameRunning()) {
			drawGameOver(g2d);
			return;
		}
	}
	
	private void drawHotTiles(Graphics2D g2d, Board board, int cellSize) {
		for(HotTile tile : board.getHotTiles()) {
			int x = tile.getGridX() * cellSize;
			int y = tile.getGridY() * cellSize;
			g2d.setColor(tile.getColor());
			g2d.fillRect(x, y, cellSize, cellSize);
			
			// Dibujar patrón de calor
			g2d.setColor(new Color(255, 69, 0));
			for(int i = 0; i < 3; i++) {
				g2d.drawLine(x + 5 + i*10, y + cellSize - 5, x + 5 + i*10, y + 5);
			}
		}
	}
	
	private void drawBonfires(Graphics2D g2d, Board board, int cellSize) {
		for(Bonfire bonfire : board.getBonfires()) {
			int x = bonfire.getGridX() * cellSize;
			int y = bonfire.getGridY() * cellSize;
			g2d.setColor(bonfire.getColor());
			
			if(bonfire.isActive()) {
				// Fuego animado
				g2d.fillPolygon(
					new int[]{x + cellSize/2, x + 5, x + cellSize - 5},
					new int[]{y + 5, y + cellSize - 5, y + cellSize - 5},
					3
				);
			} else {
				// Fogata apagada
				g2d.fillOval(x + 8, y + 8, cellSize - 16, cellSize - 16);
			}
		}
	}
	
	private void drawBlocks(Graphics2D g2d, Board board, int cellSize) {
		g2d.setColor(new Color(100, 180, 255));
		for(Block block: board.getBlocks()) {
			if(block.isVisible()) {
				int x = block.getGridX() * cellSize;
				int y = block.getGridY() * cellSize;
				g2d.fillRect(x,  y,  cellSize, cellSize);
				g2d.setColor(new Color(50, 150, 220));
				g2d.drawRect(x, y, cellSize, cellSize);
				g2d.setColor(new Color(100, 180, 255));
			}
		}
	}
	
	private void drawFruits(Graphics2D g2d, Board board, int cellSize) {
		for(Fruit fruit: board.getFruits()) {
			if(fruit.isVisibleFruit()) {
				int x = fruit.getGridX() * cellSize;
				int y = fruit.getGridY() * cellSize;
				g2d.setColor(fruit.getColor());
				g2d.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawOval(x + 5 , y + 5, cellSize - 10, cellSize - 10);
				
				// Indicador especial para Cactus con púas
				if(fruit instanceof Cactus) {
					Cactus cactus = (Cactus) fruit;
					if(cactus.hasSpikes()) {
						g2d.setColor(Color.RED);
						g2d.fillOval(x + cellSize - 8, y + 2, 6, 6);
					}
				}
			}
		}
	}
	
	private void drawIceCreams(Graphics2D g2d, Board board, int cellSize) {
		// Jugador 1 - Siempre se dibuja
		drawSingleIceCream(g2d, board.getIceCream(), cellSize);
		
		// Jugador 2 - Solo en modos PvP y PvM
		 if(gameController.getGameMode().equals("PvP") || 
			       gameController.getGameMode().equals("PvM") ||
			       gameController.getGameMode().equals("MvM")) {
			        drawSingleIceCream(g2d, board.getIceCream2(), cellSize);
			    }
			    
			    
			}
	
	private void drawSingleIceCream(Graphics2D g2d, IceCream iceCream, int cellSize) {	
		int x = iceCream.getGridX() * cellSize;
		int y = iceCream.getGridY() * cellSize;
		
		g2d.setColor(iceCream.getColor());
		g2d.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawOval(x + 5 , y + 5, cellSize - 10, cellSize - 10);
	
		 if (iceCream instanceof IceCreamAI) {
		        IceCreamAI ai = (IceCreamAI) iceCream;
		        g2d.setColor(Color.BLACK);
		        g2d.setFont(new Font("Arial", Font.BOLD, 8));
		        g2d.drawString(ai.getProfile().substring(0, 1).toUpperCase(), 
		                       x + cellSize/2 - 3, y + cellSize/2 + 3);
		    }
	
	}
	
	private void drawEnemies(Graphics2D g2d, Board board, int cellSize) {
		for(Enemy enemy: board.getEnemies()) {
			g2d.setColor(enemy.getColor());
				int x = enemy.getGridX() * cellSize;
				int y = enemy.getGridY() * cellSize;
				g2d.fillRect(x + 3,  y + 3,  cellSize - 6, cellSize - 6);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawRect(x + 3, y + 3, cellSize - 6, cellSize - 6);
		}
	}
	
	private void drawHUD(Graphics2D g2d, Board board) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		
		int hudY = board.getHeight() + 25;
		if(gameController.getGameMode().equals("PvP")) {
			// Modo PvP - Mostrar puntuaciones individuales
			g2d.drawString("P1: " + gameController.getScorePlayer1(), 10, hudY);
			g2d.drawString("P2: " + gameController.getScorePlayer2(), 100, hudY);
			g2d.drawString("Fruits: " + gameController.getFruitsCollected() + 
			               "/" + gameController.getTotalFruits(), 200, hudY);
		} else {
			// Modo individual
			g2d.drawString("Fruits: " + gameController.getFruitsCollected() + 
			               "/" + gameController.getTotalFruits(), 10, hudY);
			g2d.drawString("Score: " + gameController.getScore(), 180, hudY);
		}
		
		g2d.drawString("Time: " + gameController.getTimeRemaining() + "s", 330, hudY);
		g2d.drawString("Level " + gameController.getLevel(), 450, hudY);
	
		g2d.setFont(new Font("Arial", Font.ITALIC, 9));
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("F5: Save", 10, board.getHeight() + 45);
	
	}
	
	private void drawPause(Graphics2D g2d) {
		g2d.setFont(new Font("Arial", Font.BOLD, 40));
		g2d.setColor(new Color(0,0,0,150));
		g2d.fillRect(120, 100, 240, 160);
		g2d.setColor(Color.WHITE);
		g2d.drawString("PAUSED", 170, 200);
	}
	
	private void drawGameOver(Graphics2D g2d) {
		Board board = gameController.getBoard();
		int centerX = board.getWidth() / 2;
		int centerY = board.getHeight() / 2;
		
		// Fondo semi-transparente
		g2d.setColor(new Color(0, 0, 0, 180));
		g2d.fillRect(50, centerY - 100, board.getWidth() - 100, 220);
		
		if(gameController.didWin()) {
			// Victoria
			g2d.setFont(new Font("Arial", Font.BOLD, 40));
			g2d.setColor(Color.GREEN);
			g2d.drawString("¡LEVEL COMPLETE!", centerX - 180, centerY - 40);
			
			g2d.setFont(new Font("Arial", Font.BOLD, 24));
			g2d.setColor(Color.WHITE);
			
			if(gameController.getGameMode().equals("PvP")) {
				g2d.drawString("P1: " + gameController.getScorePlayer1() + 
				              " | P2: " + gameController.getScorePlayer2(), 
				              centerX - 100, centerY + 10);
			} else {
				g2d.drawString("Score: " + gameController.getScore(), centerX - 60, centerY + 10);
			}
			
			// Botones
			if(gameController.getLevel() < 3) {
				drawButton(g2d, 80, centerY + 60, 200, 40, "Next Level", new Color(50, 200, 50));
				drawButton(g2d, 340, centerY + 60, 200, 40, "Menu", new Color(100, 100, 200));
			} else {
				g2d.drawString("¡COMPLETED ALL LEVELS!", centerX - 160, centerY + 40);
				drawButton(g2d, 150, centerY + 60, 200, 40, "Menu", new Color(100, 100, 200));
			}
		} else {
			// Derrota
			g2d.setFont(new Font("Arial", Font.BOLD, 40));
			g2d.setColor(Color.RED);
			g2d.drawString("GAME OVER", centerX - 140, centerY);
			
			g2d.setFont(new Font("Arial", Font.PLAIN, 18));
			g2d.setColor(Color.WHITE);
			g2d.drawString("Press ENTER to retry", centerX - 100, centerY + 40);
		}
	}
	
	private void drawButton(Graphics2D g2d, int x, int y, int width, int height, String text, Color color) {
		g2d.setColor(color);
		g2d.fillRoundRect(x, y, width, height, 15, 15);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(x, y, width, height, 15, 15);
		
		g2d.setFont(new Font("Arial", Font.BOLD, 16));
		g2d.setColor(Color.WHITE);
		FontMetrics fm = g2d.getFontMetrics();
		int textX = x + (width - fm.stringWidth(text)) / 2;
		int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
		g2d.drawString(text, textX, textY);
	}
}