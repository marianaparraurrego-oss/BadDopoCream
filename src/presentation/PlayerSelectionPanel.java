package presentation;

import domain.LevelConfiguration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PlayerSelectionPanel extends JPanel{
	private MainWindow mainWindow;
	private String gameMode;
	private LevelConfiguration[] levelConfigs;
	private Color selectedColorP1 = Color.WHITE;
	private Color selectedColorP2  = Color.PINK;
	
	public PlayerSelectionPanel(MainWindow mainWindow, String gameMode, LevelConfiguration[] levelConfigs) {
		this.mainWindow = mainWindow;
		this.gameMode = gameMode;
		this.levelConfigs = levelConfigs;
		prepareElements();
		prepareActions();
	}
	
	private void prepareElements() {
		setBackground(new Color(200, 220, 255));
		setPreferredSize(new Dimension(600, 550));
	}
	
	private void prepareActions() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleClick(e.getX(), e.getY());
			}
		});
	}
	
	private void handleClick(int x, int y) {
		if(gameMode.equals("PvP")) {
			// JUGADOR1
			if(x >=100 && x <=170 && y>= 150 && y<=190) {
				selectedColorP1 = Color.WHITE;
				repaint();
				return;
			}
			if(x >= 225 && x <=295 && y>= 150 && y<=190) {
				selectedColorP1 = Color.PINK;
				repaint();
				return;
			}
			if(x >=350 && x <=420 && y>= 150 && y<= 190) {
				selectedColorP1 = new Color(139, 69, 19);
				repaint();
				return;
			}
		
		//JUGADOR2
		
		if(x >=100 && x <=170 && y>= 290 && y<= 360) {
			selectedColorP2 = Color.WHITE;
			repaint();
			return;
			
		}
		
		if(x >=225 && x <=295 && y>= 290 && y<= 360) {
			selectedColorP2 = Color.PINK;
			repaint();
			return;
			
		}
		
		if(x >=350 && x <=420 && y>= 290 && y<= 360) {
			selectedColorP2 = new Color(139, 69, 19);
			repaint();
			return;
		
		}
		
		//Start
		  if(x >= 200 && x <= 400 && y >= 420 && y <= 470){
	            mainWindow.startGameWithColor(1, gameMode, selectedColorP1, selectedColorP2, levelConfigs);
	        }
		} else {
			// Modo un jugador
			// Vanilla
			if(x >= 150 && x <= 220 && y >= 200 && y <= 270) {
				mainWindow.startGameWithColor(1, gameMode, Color.WHITE, Color.PINK, levelConfigs);
			}
			// Strawberry
			if(x >= 265 && x <= 335 && y >= 200 && y <= 270) {
				mainWindow.startGameWithColor(1, gameMode, Color.PINK, Color.WHITE, levelConfigs);
			}
			// Chocolate
			if(x >= 380 && x <= 450 && y >= 200 && y <= 270) {
				mainWindow.startGameWithColor(1, gameMode, new Color(139, 69, 19), Color.PINK, levelConfigs);
			}
		}
	}
	
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//SeleccionRol
		if(gameMode.equals("PvP")) {
			drawPvPSelection(g2d);
		} else {
			drawSinglePlayerSelection(g2d);
		}
	}
		
	
	private void drawPvPSelection(Graphics2D g2d) {
		// Título
		g2d.setFont(new Font("Arial", Font.BOLD, 32));
		g2d.setColor(Color.BLACK);
		g2d.drawString("Player vs Player", 150, 50);
		
		// Player 1 Selection
		g2d.setFont(new Font("Arial", Font.BOLD, 22));
		g2d.setColor(new Color(50, 100, 200));
		g2d.drawString("Player 1 - Choose Color", 150, 100);
		
		drawIceCreamOption(g2d, 85, 135, Color.WHITE, "Vanilla", selectedColorP1.equals(Color.WHITE));
		drawIceCreamOption(g2d, 210, 135, Color.PINK, "Strawberry", selectedColorP1.equals(Color.PINK));
		drawIceCreamOption(g2d, 335, 135, new Color(139, 69, 19), "Chocolate", selectedColorP1.equals(new Color(139, 69, 19)));
		
		// Separador
		g2d.setColor(new Color(100, 150, 200));
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(50, 240, 550, 240);
		
		// Player 2 Selection
		g2d.setFont(new Font("Arial", Font.BOLD, 22));
		g2d.setColor(new Color(200, 50, 100));
		g2d.drawString("Player 2 - Choose Color", 150, 270);
		
		drawIceCreamOption(g2d, 85, 275, Color.WHITE, "Vanilla", selectedColorP2.equals(Color.WHITE));
		drawIceCreamOption(g2d, 210, 275, Color.PINK, "Strawberry", selectedColorP2.equals(Color.PINK));
		drawIceCreamOption(g2d, 335, 275, new Color(139, 69, 19), "Chocolate", selectedColorP2.equals(new Color(139, 69, 19)));
		
		// Botón Start
		g2d.setColor(new Color(50, 200, 50));
		g2d.fillRoundRect(200, 420, 200, 50, 20, 20);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(200, 420, 200, 50, 20, 20);
		
		g2d.setFont(new Font("Arial", Font.BOLD, 24));
		g2d.setColor(Color.WHITE);
		g2d.drawString("START GAME", 220, 453);
		
		// Controles
		g2d.setFont(new Font("Arial", Font.ITALIC, 12));
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("P1: Arrows + Space  |  P2: WASD + Shift", 150, 390);
	}
	
	private void drawSinglePlayerSelection(Graphics2D g2d) {
		
		// Título
		g2d.setFont(new Font("Arial", Font.BOLD, 25));
		g2d.setColor(Color.BLACK);
		String stepText = "Choose your ice cream";
		g2d.drawString(stepText, 140, 80);
		
		// Opciones
		drawIceCreamOption(g2d, 135, 185, Color.WHITE, "Vanilla", false);
		drawIceCreamOption(g2d, 250, 185, Color.PINK, "Strawberry", false);
		drawIceCreamOption(g2d, 335, 185, new Color(139, 69, 19), "Chocolate", false);
	}
	
	private void drawIceCreamOption(Graphics2D g2d, int x, int y, Color color, String name, boolean selected) {
		// Círculo de color
		g2d.setColor(color);
		g2d.fillOval(x + 15, y + 15, 60, 60);
		
		// Borde (más grueso si está seleccionado)
		if (selected) {
	        g2d.setColor(Color.YELLOW);
	        g2d.setStroke(new BasicStroke(4));
	        g2d.drawOval(x + 15, y + 15, 60, 60);
	    }

	    // Texto debajo
	    g2d.setColor(Color.BLACK);
	    g2d.setFont(new Font("Arial", Font.BOLD, 14));
	    g2d.drawString(name, x + 20, y + 95);
	}
}