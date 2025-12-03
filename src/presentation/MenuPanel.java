package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel{
	private MainWindow mainWindow;
	private String selectedMode = "PvP";
	
	public MenuPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		prepareElements();
		prepareActions();
	}
	
	private void prepareElements() {
		setBackground(new Color(200, 220, 255));
		setPreferredSize(new Dimension(600,500));
	}
	
	private void prepareActions() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMenuClick(e.getX(), e.getY());
			}
		});
	
	}
	
	private void handleMenuClick(int x, int y) {
		// Botón Player (1 jugador)
		if(x >= 150 && x <= 450 && y >= 150 && y <= 210) {
			selectedMode = "Player";
			mainWindow.startGame(1, "Player");
		}
				
		// Botón PvP (2 jugadores)
		if(x >= 150 && x <= 450 && y >= 230 && y <= 290) {
			selectedMode = "PvP";
			mainWindow.startGame(1, "PvP");
		}
				
		// Botón PvM (Jugador vs Máquina)
		if(x >= 150 && x <= 450 && y >= 310 && y <= 370) {
			selectedMode = "PvM";
			mainWindow.startGame(1, "PvM");
		}
		// Botón MvM (Máquina vs Máquina)
		if(x >= 150 && x <= 450 && y >= 390 && y <= 450) {
			selectedMode = "MvM";
			mainWindow.startGame(1, "MvM");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Title
		g2d.setFont(new Font("Arial", Font.BOLD, 50));
		g2d.setColor(new Color(50, 100 ,200));
		g2d.drawString("BADDOPOCREAM", 80, 80);
		
		//Subtitle
		g2d.setFont(new Font("Arial", Font.PLAIN, 20));
		g2d.setColor(Color.BLACK);
		g2d.drawString("Select game mode", 150, 120);
		
		//Buttons
		drawButton(g2d, 150, 150, 300, 60, "Player ", selectedMode.equals("Player"));
		drawButton(g2d, 150, 230, 300, 60, "PvP", selectedMode.equals("PvP"));
		drawButton(g2d, 150, 310, 300, 60, "PvM", selectedMode.equals("PvM"));
		drawButton(g2d, 150, 390, 300, 60, "MvM", selectedMode.equals("MvM"));
		
		//Instructions
		g2d.setFont(new Font("Arial", Font.ITALIC, 14));
		g2d.setColor(new Color(100, 100, 100));
		g2d.drawString("Click a mode to start", 210, 480);
	}
	
	private void drawButton(Graphics2D g2d, int x, int y, int width, int height, String text, boolean selected) {
		if(selected) {
			g2d.setColor(new Color(100, 150, 255));
		}else {
			g2d.setColor(new Color(150, 180, 255));
		}
		
		g2d.fillRoundRect(x, y, width, height, 15, 15);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(x, y, width, height, 15, 15);
		
		g2d.setFont(new Font("Arial", Font.BOLD,16));
		g2d.setColor(Color.WHITE);
		FontMetrics fm = g2d.getFontMetrics();
		int textX = x + (width- fm.stringWidth(text)) / 2;
		int textY = y + ((height- fm.getHeight()) / 2)+ fm.getAscent();
		g2d.drawString(text, textX, textY);
	}
	
}
