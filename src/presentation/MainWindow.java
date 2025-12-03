package presentation;

import domain.*;
import javax.swing.*;
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
		
		
		PlayerSelectionPanel selectionPanel = new PlayerSelectionPanel(this, gameMode);
		add(selectionPanel);
		pack();
		revalidate();
		repaint();

		
	}
	
	public void startGameWithColor(int level, String gameMode, Color iceCreamColor, Color iceCreamColor2) {
		getContentPane().removeAll();
		
		gameController = new GameController(level, gameMode, iceCreamColor, iceCreamColor2);
		gamePanel = new GamePanel(gameController);
		gamePanel.setPreferredSize(new Dimension(540, 530));
		
		add(gamePanel);
		pack();
		revalidate();
		repaint();
		
		gamePanel.requestFocus();
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
