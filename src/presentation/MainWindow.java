package presentation;

import domain.*;
import javax.swing.*;

import exceptions.BadDopoCreamExceptions;

import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private GameController gameController;
	
	public MainWindow() {
		prepareElements();
		prepareActions();
	}
	
	private void prepareElements() {
		setTitle("BADDOPOCREAM");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		menuPanel = new MenuPanel(this);
		add(menuPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void prepareActions() {
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				exitWithConfirmation();
			}
		});
	}
	
	public void startGame(int level, String gameMode) {
		getContentPane().removeAll();
		
		
		LevelConfigPanel configPanel = new LevelConfigPanel(this, gameMode);
		add(configPanel);
		pack();
		revalidate();
		repaint();
		
	}
	/**
	 * Inicia el juego con configuraciones de niveles
	 */
	public void startGameWithConfig(String gameMode, LevelConfiguration[] levelConfigs) {
	    getContentPane().removeAll();
	    
	    PlayerSelectionPanel selectionPanel = new PlayerSelectionPanel(this, gameMode, levelConfigs);
	    add(selectionPanel);
	    pack();
	    revalidate();
	    repaint();
	}
	
	public void startGameWithColor(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2, LevelConfiguration[] levelConfigs,String aiProfile1, String aiProfile2) {
	getContentPane().removeAll();
	
	gameController = new GameController(level, gameMode, iceCreamColor, iceCreamColor2, levelConfigs, aiProfile1, aiProfile2);
	gamePanel = new GamePanel(gameController, this);
	gamePanel.setPreferredSize(new Dimension(540, 530));
	
	add(gamePanel);
	pack();
	revalidate();
	repaint();
	
	gamePanel.requestFocus();
	}

	//Método sobrecargado para compatibilidad (sin perfiles)
	public void startGameWithColor(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2, LevelConfiguration[] levelConfigs) {
	// Determinar perfiles según el modo de juego
		String profile1 = null;
		String profile2 = null;
		
		if (gameMode.equals("PvM")) {
		profile1 = null; // Jugador 1 humano
		profile2 = "expert"; // Jugador 2 IA
		} else if (gameMode.equals("MvM")) {
		profile1 = "expert"; // Jugador 1 IA
		profile2 = "expert"; // Jugador 2 IA
		}
		// Para Player y PvP, ambos perfiles son null (sin IA)
		
		startGameWithColor(level, gameMode, iceCreamColor, iceCreamColor2, levelConfigs, profile1, profile2);
	}
	/**
	 * Carga una partida desde un GameState
	 */
	public void loadGame(GameState state) {
		getContentPane().removeAll();
		
		gameController = new GameController(1, state.getGameMode(),state.getIceCreamColor(), state.getIceCreamColor2(), state.getLevelConfigs());
		try {
	        gameController.loadFromState(state);
	    } catch (BadDopoCreamExceptions e) {
	        // Mostrar mensaje de error al usuario
	        JOptionPane.showMessageDialog(this, 
	            e.getMessage(),
	            "Error al cargar partida", 
	            JOptionPane.ERROR_MESSAGE);
	        
	        returnToMenu();
	        return;
	    }
		gamePanel = new GamePanel(gameController, this);
		gamePanel.setPreferredSize(new Dimension(540, 530));
		
		add(gamePanel);
		pack();
		revalidate();
		repaint();
		
		gamePanel.requestFocus();
	}
	
	/**
	 * Muestra el panel de guardado
	 */
	public void showSavePanel() {
		if(gameController != null) {
			getContentPane().removeAll();
			
			SaveLoadPanel savePanel = new SaveLoadPanel(this, true);
			add(savePanel);
			pack();
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Muestra el panel de carga
	 */
	public void showLoadPanel() {
		getContentPane().removeAll();
		
		SaveLoadPanel loadPanel = new SaveLoadPanel(this, false);
		add(loadPanel);
		pack();
		revalidate();
		repaint();
	}
	
	public void returnToMenu() {
		getContentPane().removeAll();
		
		menuPanel = new MenuPanel(this);
		add(menuPanel);
		pack();
		revalidate();
		repaint();
	}
	
	/**
	 * Obtiene el controlador del juego actual
	 */
	public GameController getGameController() {
		return gameController;
	}
	
	private void exitWithConfirmation() {
		int response = JOptionPane.showConfirmDialog(
				this, "Do you want to exit BADDOPOCREAM", 
				"Confirm exit", 
				JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}
