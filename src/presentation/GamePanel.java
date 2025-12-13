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
	
	// Imágenes del juego
	private Image iceBlock;
	private Image fondIce;
		
	// Frutas
	private Image grapes;
	private Image banana;
	private Image cherry;
	private Image pineapple;
	private Image cactus;
		
	// Helados
	private Image iceVainilla;
	private Image iceFresa;
	private Image iceChocolate;
		
	// Enemigos
	private Image troll;
	private Image pot;
	private Image orangeSquid;
	private Image narval;
		
	// Obstáculos
	private Image bonfire;
	private Image bonfireOff;
	private Image hotTile;
	
	private Image paused;
	private Image win;
	private Image gameOver;
	private Image button;
	private Image igloo;
	
	public GamePanel(GameController gameController, MainWindow mainWindow) {
		this.gameController = gameController;
		this.mainWindow = mainWindow;
		this.keysPressed = new HashSet<>();
		
		prepareElements();
		prepareActions();
		startGameLoop();
		loadImage();
	}
	
	private void loadImage() {
		iceBlock = new ImageIcon(getClass().getResource("/presentation/imagenes/bloquehielo.png")).getImage();
		fondIce = new ImageIcon(getClass().getResource("/presentation/imagenes/fondonieve.png")).getImage();
		
		grapes = new ImageIcon(getClass().getResource("/presentation/imagenes/uvas.png")).getImage();
		banana = new ImageIcon(getClass().getResource("/presentation/imagenes/platano.png")).getImage();
		cherry = new ImageIcon(getClass().getResource("/presentation/imagenes/cereza.png")).getImage();
		pineapple = new ImageIcon(getClass().getResource("/presentation/imagenes/piña.png")).getImage();
		cactus = new ImageIcon(getClass().getResource("/presentation/imagenes/cactus.png")).getImage();

		iceVainilla = new ImageIcon(getClass().getResource("/presentation/imagenes/vainillacream.png")).getImage();
		iceFresa = new ImageIcon(getClass().getResource("/presentation/imagenes/fresacream.png")).getImage();
		iceChocolate = new ImageIcon(getClass().getResource("/presentation/imagenes/chocolatecream.png")).getImage();
		
		troll = new ImageIcon(getClass().getResource("/presentation/imagenes/troll.png")).getImage();
	    pot = new ImageIcon(getClass().getResource("/presentation/imagenes/maceta.png")).getImage();
	    orangeSquid = new ImageIcon(getClass().getResource("/presentation/imagenes/calamar.png")).getImage();
	    narval = new ImageIcon(getClass().getResource("/presentation/imagenes/narval.png")).getImage();
	  	
		bonfire = new ImageIcon(getClass().getResource("/presentation/imagenes/fogata.png")).getImage();
		bonfireOff = new ImageIcon(getClass().getResource("/presentation/imagenes/fogataapagada.png")).getImage();
		hotTile = new ImageIcon(getClass().getResource("/presentation/imagenes/baldosa.png")).getImage();
		
		paused = new ImageIcon(getClass().getResource("/presentation/imagenes/pausar.png")).getImage();
		win = new ImageIcon(getClass().getResource("/presentation/imagenes/completed.png")).getImage();
		gameOver = new ImageIcon(getClass().getResource("/presentation/imagenes/perdida.png")).getImage();
		button = new ImageIcon(getClass().getResource("/presentation/imagenes/boton.png")).getImage();
		igloo = new ImageIcon(getClass().getResource("/presentation/imagenes/iglu.png")).getImage();
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
				            System.out.println("Avanzando a nivel " + (currentLevel + 1)); // Debug
				            gameController.nextLevel();
				            repaint();
				            requestFocus();
				        } else {
				            System.out.println("Juego completado, regresando al menú"); // Debug
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
		            int centerX = getWidth() / 2;
		            int centerY = getHeight() / 2;
		            int panelH = 220;
		            int panelY = centerY - panelH / 2 - 40;
		            int btnY = panelY + panelH + 10;
		            
		            // Botón "NEXT LEVEL"
		            if(gameController.getLevel() < 3) {
		                
		                if(e.getX() >= centerX - 220 && e.getX() <= centerX - 40 && 
		                   e.getY() >= btnY && e.getY() <= btnY + 45) {
		                    System.out.println("Click en NEXT LEVEL"); 
		                    gameController.nextLevel();
		                    revalidate();
		                    repaint();
		                    requestFocus();
		                }
		                
		                if(e.getX() >= centerX + 40 && e.getX() <= centerX + 220 && 
		                   e.getY() >= btnY && e.getY() <= btnY + 45) {
		                    System.out.println("Click en MENU"); 
		                    mainWindow.returnToMenu();
		                }
		            } else {

		                if(e.getX() >= centerX - 90 && e.getX() <= centerX + 90 && 
		                   e.getY() >= btnY && e.getY() <= btnY + 45) {
		                    System.out.println("Click en MENU"); 
		                    mainWindow.returnToMenu();
		                }
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
		
		if(fondIce != null) {
			g2d.drawImage(fondIce, 0, 0, board.getWidth(), board.getHeight(), null);
		} else {
			g2d.setColor(board.getColor());
			g2d.fillRect(0, 0, board.getWidth(), board.getHeight());
		}

		g2d.setColor(new Color(255, 255, 255, 30));
		for(int i = 0; i <= board.getRows(); i++) {
			g2d.drawLine(0, i*cellSize, board.getWidth(), i*cellSize);
		}
		for(int j = 0; j <= board.getCols(); j++) {
			g2d.drawLine(j*cellSize, 0, j*cellSize, board.getHeight());
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
		
		// Iglu
		drawIgloo(g2d, board, cellSize);
		
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
	
	private void drawIgloo(Graphics2D g2d, Board board, int cellSize) {
	    
	    int centerX = board.getCols() / 2;
	    int centerY = board.getRows() / 2;

	    int iglooX = (centerX - 2) * cellSize;
	    int iglooY = (centerY - 2) * cellSize;
	    int iglooSize = cellSize * 4;
	    
	    if (igloo != null) {
	     
	        g2d.drawImage(igloo, iglooX, iglooY, iglooSize, iglooSize, null);
	    } else {

	        g2d.setColor(new Color(200, 230, 255));
	        g2d.fillRect(iglooX, iglooY, iglooSize, iglooSize);
	        
	        g2d.setColor(new Color(150, 200, 255));
	        g2d.setStroke(new BasicStroke(3));
	        g2d.drawRect(iglooX, iglooY, iglooSize, iglooSize);
	        
	        g2d.setColor(Color.BLACK);
	        g2d.setFont(new Font("Arial", Font.BOLD, 16));
	        FontMetrics fm = g2d.getFontMetrics();
	        String text = "IGLOO";
	        int textX = iglooX + (iglooSize - fm.stringWidth(text)) / 2;
	        int textY = iglooY + iglooSize / 2;
	        g2d.drawString(text, textX, textY);
	    }
	}
	
	private void drawHotTiles(Graphics2D g2d, Board board, int cellSize) {
		for(HotTile tile : board.getHotTiles()) {
			int x = tile.getGridX() * cellSize;
			int y = tile.getGridY() * cellSize;
			
			if(hotTile != null) {
				g2d.drawImage(hotTile, x, y, cellSize, cellSize, null);
			} else {
				g2d.setColor(tile.getColor());
				g2d.fillRect(x, y, cellSize, cellSize);
			}
		}
	}
	
	private void drawBonfires(Graphics2D g2d, Board board, int cellSize) {
		for(Bonfire bfire : board.getBonfires()) {
			int x = bfire.getGridX() * cellSize;
			int y = bfire.getGridY() * cellSize;
			
			if(bfire.isActive()) {
				if(bonfire != null) {
					g2d.drawImage(bonfire, x, y, cellSize, cellSize, null);
				} else {
					g2d.setColor(bfire.getColor());
					g2d.fillPolygon(
						new int[]{x + cellSize/2, x + 5, x + cellSize - 5},
						new int[]{y + 5, y + cellSize - 5, y + cellSize - 5},
						3
					);
				}
			} else {
				// Fogata apagada
				if(bonfireOff != null) {
					g2d.drawImage(bonfireOff, x, y, cellSize, cellSize, null);
				} else {
					g2d.setColor(Color.GRAY);
					g2d.fillOval(x + 8, y + 8, cellSize - 16, cellSize - 16);
				}
			}
		}
	}
	
	private void drawBlocks(Graphics2D g2d, Board board, int cellSize) {
		for(Block block: board.getBlocks()) {
			if(block.isVisible()) {
				int x = block.getGridX() * cellSize;
				int y = block.getGridY() * cellSize;
				
				if(iceBlock != null) {
					g2d.drawImage(iceBlock, x, y, cellSize, cellSize, null);
				} else {
					g2d.setColor(new Color(100, 180, 255));
					g2d.fillRect(x, y, cellSize, cellSize);
					g2d.setColor(new Color(50, 150, 220));
					g2d.drawRect(x, y, cellSize, cellSize);
				}
			}
		}
	}
	
	private void drawFruits(Graphics2D g2d, Board board, int cellSize) {
		for(Fruit fruit: board.getFruits()) {
			if(fruit.isVisibleFruit()) {
				int x = fruit.getGridX() * cellSize;
				int y = fruit.getGridY() * cellSize;
				
				Image fruitImg = getFruitImage(fruit);
				if(fruitImg != null) {
					g2d.drawImage(fruitImg, x, y, cellSize, cellSize, null);
				} else {
					g2d.setColor(fruit.getColor());
					g2d.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10);
				}
			}
		}
	}
	
	private Image getFruitImage(Fruit fruit) {
		String fruitType = fruit.getClass().getSimpleName();
		switch(fruitType) {
			case "Grapes": return grapes;
			case "Banana": return banana;
			case "Cherry": return cherry;
			case "Pineapple": return pineapple;
			case "Cactus": return cactus;
			default: return null;
		}
	}
	
	private void drawIceCreams(Graphics2D g2d, Board board, int cellSize) {
		// Jugador 1 - Siempre se dibuja
		drawSingleIceCream(g2d, board.getIceCream(), cellSize);
		
		if(gameController.getGameMode().equals("PvP") || 
		   gameController.getGameMode().equals("PvM") ||
		   gameController.getGameMode().equals("MvM")) {
			drawSingleIceCream(g2d, board.getIceCream2(), cellSize);
		}	    
	}
	
	private void drawSingleIceCream(Graphics2D g2d, IceCream iceCream, int cellSize) {	
		int x = iceCream.getGridX() * cellSize;
		int y = iceCream.getGridY() * cellSize;
		
		Image iceImg = getIceCreamImage(iceCream);
		if(iceImg != null) {
			g2d.drawImage(iceImg, x, y, cellSize, cellSize, null);
		} else {
			g2d.setColor(iceCream.getColor());
			g2d.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10);
		}
		
		if(iceCream instanceof IceCreamAI) {
			IceCreamAI ai = (IceCreamAI) iceCream;
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.BOLD, 8));
			g2d.drawString(ai.getProfile().substring(0, 1).toUpperCase(), 
			               x + cellSize/2 - 3, y + cellSize/2 + 3);
		}
	}
	
	private Image getIceCreamImage(IceCream iceCream) {
		Color color = iceCream.getColor();
		if(color.equals(Color.WHITE)) {
			return iceVainilla;
		} else if(color.equals(Color.PINK)) {
			return iceFresa;
		} else if(color.equals(new Color(139, 69, 19))) {
			return iceChocolate;
		}
		return null;
	}
	
	private void drawEnemies(Graphics2D g2d, Board board, int cellSize) {
		for(Enemy enemy: board.getEnemies()) {
	        int x = enemy.getGridX() * cellSize;
	        int y = enemy.getGridY() * cellSize;
	        
	        String enemyType = enemy.getClass().getSimpleName();
	        
	        Image enemyImg = getEnemyImage(enemy);
	        
	        if(enemyImg != null) {
	            g2d.drawImage(enemyImg, x, y, cellSize, cellSize, null);
	        } else {
	            
	            g2d.setColor(enemy.getColor());
	            g2d.fillRect(x + 3, y + 3, cellSize - 6, cellSize - 6);
	            
	            g2d.setColor(Color.BLACK);
	            g2d.setStroke(new BasicStroke(2));
	            g2d.drawRect(x + 3, y + 3, cellSize - 6, cellSize - 6);

	            g2d.setFont(new Font("Arial", Font.BOLD, 12));
	            g2d.setColor(Color.WHITE);
	            String letter = enemyType.substring(0, 1);
	            g2d.drawString(letter, x + cellSize/2 - 4, y + cellSize/2 + 4);
	        }
	    }
	}
	
	
	private Image getEnemyImage(Enemy enemy) {
		String enemyType = enemy.getClass().getSimpleName();

	    if(enemyType.equals("PatrollingEnemy") || enemyType.equals("Troll")) {
	        return troll;
	    } else if(enemyType.equals("Pot")) {
	        return pot;
	    } else if(enemyType.equals("OrangeSquid")) {
	        return orangeSquid;
	    } else if(enemyType.equals("Narval")) {
	        return narval;
	    }
	    return null;
	}
	
	private void drawHUD(Graphics2D g2d, Board board) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		
		int hudY = board.getHeight() + 25;
		if(gameController.getGameMode().equals("PvP")) {
			g2d.drawString("P1: " + gameController.getScorePlayer1(), 10, hudY);
			g2d.drawString("P2: " + gameController.getScorePlayer2(), 100, hudY);
			g2d.drawString("Fruits: " + gameController.getFruitsCollected() + 
			               "/" + gameController.getTotalFruits(), 200, hudY);
		} else {
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
		Board board = gameController.getBoard();

	    int imgW = 300;
	    int imgH = 150;

	    int x = (board.getWidth() - imgW) / 2;
	    int y = (board.getHeight() - imgH) / 2;

	    // Oscurecer fondo
	    g2d.setColor(new Color(0, 0, 0, 150));
	    g2d.fillRect(0, 0, board.getWidth(), board.getHeight());

	    if (paused != null) {
	        g2d.drawImage(paused, x, y, imgW, imgH, null);
	    }
	 }
	
	private void drawGameOver(Graphics2D g2d) {
		int centerX = getWidth() / 2;
	    int centerY = getHeight() / 2;
	    int panelW = 420;
	    int panelH = 220;

	    int panelX = centerX - panelW / 2;
	    int panelY = centerY - panelH / 2 - 40;

	    g2d.drawImage(win, panelX, panelY, panelW, panelH, null);

	    Color darkGold = new Color(139, 90, 0);
	    Color gold = new Color(218, 165, 32);
	    Color lightGold = new Color(255, 215, 0);

	    g2d.setColor(darkGold);
	    g2d.setStroke(new BasicStroke(6));
	    g2d.drawRoundRect(panelX - 3, panelY - 3, panelW + 6, panelH + 6, 10, 10);
	    
	    g2d.setColor(gold);
	    g2d.setStroke(new BasicStroke(3));
	    g2d.drawRoundRect(panelX - 1, panelY - 1, panelW + 2, panelH + 2, 10, 10);
	    g2d.setColor(lightGold);
	    g2d.setStroke(new BasicStroke(1));
	    g2d.drawRect(panelX, panelY, panelW, panelH);

	    g2d.setFont(new Font("Arial", Font.BOLD, 18));

	    if (gameController.getGameMode().equals("PvP")) {
	        // P1 Score con contorno negro
	        String p1Text = "P1: " + gameController.getScorePlayer1();
	        int p1X = panelX + 100;
	        int p1Y = panelY + 135;
	        
	        g2d.setColor(Color.BLACK);
	        for (int dx = -1; dx <= 1; dx++) {
	            for (int dy = -1; dy <= 1; dy++) {
	                if (dx != 0 || dy != 0) {
	                    g2d.drawString(p1Text, p1X + dx, p1Y + dy);
	                }
	            }
	        }
	        g2d.setColor(Color.WHITE);
	        g2d.drawString(p1Text, p1X, p1Y);
	        
	        // P2 Score con contorno negro
	        String p2Text = "P2: " + gameController.getScorePlayer2();
	        int p2X = panelX + 265;
	        int p2Y = panelY + 135;
	        
	        g2d.setColor(Color.BLACK);
	        for (int dx = -1; dx <= 1; dx++) {
	            for (int dy = -1; dy <= 1; dy++) {
	                if (dx != 0 || dy != 0) {
	                    g2d.drawString(p2Text, p2X + dx, p2Y + dy);
	                }
	            }
	        }
	        g2d.setColor(Color.WHITE);
	        g2d.drawString(p2Text, p2X, p2Y);
	        
	    	} else {
	   
	        String scoreText = "Score: " + gameController.getScore();
	        int scoreX = panelX + 165;
	        int scoreY = panelY + 140;
	        
	        g2d.setColor(Color.BLACK);
	        for (int dx = -1; dx <= 1; dx++) {
	            for (int dy = -1; dy <= 1; dy++) {
	                if (dx != 0 || dy != 0) {
	                    g2d.drawString(scoreText, scoreX + dx, scoreY + dy);
	                }
	            }
	        }
	        g2d.setColor(Color.WHITE);
	        g2d.drawString(scoreText, scoreX, scoreY);
	    	}

	    	int btnY = panelY + panelH + 10;

	    	if (gameController.getLevel() < 3) {
	        // BOTÓN NEXT LEVEL
	        g2d.drawImage(button, centerX - 220, btnY, 180, 45, null);
	        
	        g2d.setFont(new Font("Arial", Font.BOLD, 16));
	        String nextText = "NEXT LEVEL";
	        int nextX = centerX - 175;
	        int nextY = btnY + 28;
	        
	        g2d.setColor(Color.BLACK);
	        for (int dx = -2; dx <= 2; dx++) {
	            for (int dy = -2; dy <= 2; dy++) {
	                if (dx != 0 || dy != 0) {
	                    g2d.drawString(nextText, nextX + dx, nextY + dy);
	                }
	            }
	        }

	        g2d.setColor(Color.YELLOW);
	        g2d.drawString(nextText, nextX, nextY);

	        // BOTÓN MENU
	        g2d.drawImage(button, centerX + 40, btnY, 180, 45, null);
	        
	        String menuText = "MENU";
	        int menuX = centerX + 105;
	        int menuY = btnY + 28;
	        
	        g2d.setColor(Color.BLACK);
	        for (int dx = -2; dx <= 2; dx++) {
	            for (int dy = -2; dy <= 2; dy++) {
	                if (dx != 0 || dy != 0) {
	                    g2d.drawString(menuText, menuX + dx, menuY + dy);
	                }
	            }
	        }

	        g2d.setColor(Color.YELLOW);
	        g2d.drawString(menuText, menuX, menuY);
	        
	    	} else {
	        // SOLO BOTÓN MENU
	        g2d.drawImage(button, centerX - 90, btnY, 180, 45, null);
	        
	        g2d.setFont(new Font("Arial", Font.BOLD, 16));
	        String menuText = "MENU";
	        int menuX = centerX - 25;
	        int menuY = btnY + 28;
	        
	        // Contorno negro grueso
	        g2d.setColor(Color.BLACK);
	        for (int dx = -2; dx <= 2; dx++) {
	            for (int dy = -2; dy <= 2; dy++) {
	                if (dx != 0 || dy != 0) {
	                    g2d.drawString(menuText, menuX + dx, menuY + dy);
	                }
	            }
	        }
	     
	        g2d.setColor(Color.YELLOW);
	        g2d.drawString(menuText, menuX, menuY);
	    }
	}
}