package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel{
	private MainWindow mainWindow;
	private String selectedMode = "PvP";
	private Image bg;
    private Image buttonSelected;
	private Font pixelFont = new Font("Arial", Font.BOLD, 26);
	
	public MenuPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		loadImagen();
		prepareElements();
		prepareActions();
	}
	
	private void loadImagen(){
        bg = new ImageIcon(getClass().getResource("/presentation/imagenes/fondo principal.png")).getImage();
        buttonSelected = new ImageIcon(getClass().getResource("/presentation/imagenes/boton.png")).getImage();

    }
	
	private void prepareElements() {
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
		if(x >= 150 && x <= 450 && y >= 140 && y <= 210) {
			selectedMode = "Player";
			mainWindow.startGame(1, "Player");
		}
				
		// Botón PvP (2 jugadores)
		if(x >= 150 && x <= 450 && y >= 210 && y <= 290) {
			selectedMode = "PvP";
			mainWindow.startGame(1, "PvP");
		}
				
		// Botón PvM (Jugador vs Máquina)
		if(x >= 150 && x <= 450 && y >= 280 && y <= 370) {
			selectedMode = "PvM";
			mainWindow.startGame(1, "PvM");
		}
		// Botón MvM (Máquina vs Máquina)
		if(x >= 150 && x <= 450 && y >= 350 && y <= 450) {
			selectedMode = "MvM";
			mainWindow.startGame(1, "MvM");
		}
		
		// Botón Load Game
		if(x >= 150 && x <= 450 && y >= 420 && y <= 500) {
			mainWindow.showLoadPanel();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
		
		//Title
		g2d.setFont(new Font("Arial", Font.BOLD, 50));
				
		g2d.setColor(Color.BLACK);
			for (int dx = -3; dx <= 3; dx++) {
				for (int dy = -3; dy <= 3; dy++) {
					if (dx != 0 || dy != 0) {
						g2d.drawString("BAD DOPO CREAM", 85 + dx, 80 + dy);
					}
				}
			}
			
		g2d.setColor(Color.WHITE);
		g2d.drawString("BAD DOPO CREAM", 85, 80);
		
		//Subtitle
		g2d.setFont(new Font("Arial", Font.BOLD, 22));
		g2d.setColor(Color.BLACK);
		for (int dx = -2; dx <= 2; dx++) {
			for (int dy = -2; dy <= 2; dy++) {
				if (dx != 0 || dy != 0) {
					g2d.drawString("Select game mode", 200 + dx, 120 + dy);
				}
			}
		}
		g2d.setColor(Color.BLUE);
		g2d.drawString("Select game mode", 200, 120);
		
		//Buttons
		drawButton(g2d, 150, 140, 300, 60, "Player ", selectedMode.equals("Player"));
		drawButton(g2d, 150, 210, 300, 60, "PvP", selectedMode.equals("PvP"));
		drawButton(g2d, 150, 280, 300, 60, "PvM", selectedMode.equals("PvM"));
		drawButton(g2d, 150, 350, 300, 60, "MvM", selectedMode.equals("MvM"));
		drawButton(g2d, 150, 420, 300, 60, "Load Game", false);
		//Instructions
		//g2d.setFont(new Font("Arial", Font.ITALIC, 14));
		//g2d.setColor(new Color(100, 100, 100));
		//g2d.drawString("Click a mode to start", 210, 480);
	}
	
	private void drawButton(Graphics2D g2d, int x, int y, int width, int height, String text, boolean selected) {
		//Fondo del boton
		if (buttonSelected != null) {
            g2d.drawImage(buttonSelected, x, y, width, height, null);
        } else {
            g2d.setColor(new Color(150, 100, 50));
            g2d.fillRoundRect(x, y, width, height, 15, 15);
        }
		
		//Borde si esta seleccionado
		if (selected) {
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(new Color(255, 255, 0));
            g2d.drawRoundRect(x + 2, y + 2, width - 4, height - 4, 15, 15);
        }
		
		g2d.setFont(new Font("Arial", Font.BOLD, 24));
		FontMetrics fm = g2d.getFontMetrics();
		int textX = x + (width - fm.stringWidth(text)) / 2;
		int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
		
		g2d.setColor(Color.BLACK);
		for (int dx = -2; dx <= 2; dx++) {
			for (int dy = -2; dy <= 2; dy++) {
				if (dx != 0 || dy != 0) {
					g2d.drawString(text, textX + dx, textY + dy);
				}
			}
		}
		
		g2d.setColor(Color.YELLOW);
		g2d.drawString(text, textX, textY);
	}
	
}
