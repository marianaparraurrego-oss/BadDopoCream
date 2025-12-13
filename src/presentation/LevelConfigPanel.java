package presentation;

import domain.LevelConfiguration;
import javax.swing.*;

import exceptions.BadDopoCreamExceptions;

import java.awt.*;
import java.awt.event.*;

public class LevelConfigPanel extends JPanel {
    private MainWindow mainWindow;
    private String gameMode;
    private LevelConfiguration[] levelConfigs;

    private JComboBox<String>[] fruit1Combos;
    private JComboBox<String>[] fruit2Combos;
    private JComboBox<String>[] enemyCombos;
    private JComboBox<String>[] obstacleCombos;

    private String[] fruitTypes = { "Grapes", "Banana", "Cherry", "Pineapple", "Cactus" };
    private String[] enemyTypes = { "Troll", "Pot", "OrangeSquid", "Narval" };
    private String[] obstacleTypes = { "Bonfire", "HotTile", "Both" };
    
    private Image bg;
    private Image bgPattern;
    private Image buttonStart;

    @SuppressWarnings("unchecked")
    public LevelConfigPanel(MainWindow mainWindow, String gameMode) {
        this.mainWindow = mainWindow;
        this.gameMode = gameMode;
        this.levelConfigs = new LevelConfiguration[3];

        fruit1Combos = new JComboBox[3];
        fruit2Combos = new JComboBox[3];
        enemyCombos = new JComboBox[3];
        obstacleCombos = new JComboBox[3];
        
        loadImages();
        prepareElements();
        prepareActions();
    }
    
    private void loadImages() {
        bg = new ImageIcon(getClass().getResource("/presentation/imagenes/fondo principal.png")).getImage();
        bgPattern = new ImageIcon(getClass().getResource("/presentation/imagenes/FONDO.png")).getImage();
        buttonStart = new ImageIcon(getClass().getResource("/presentation/imagenes/boton.png")).getImage();
    }

    private void prepareElements() {
        setLayout(null);
        setPreferredSize(new Dimension(600, 650));

        // NIVEL 1
        int level1Y = 125;
        addLevelComponents(0, level1Y);

        // NIVEL 2
        int level2Y = 290;
        addLevelComponents(1, level2Y);

        // NIVEL 3
        int level3Y = 455;
        addLevelComponents(2, level3Y);
    }
    
    private void addLevelComponents(int levelIndex, int yPosition) {
        int comboX = 265;
        int comboWidth = 160;
        int comboHeight = 28;
        int spacing = 35;
        int startY = yPosition - 25; 

        // Fruit 1
        fruit1Combos[levelIndex] = new JComboBox<>(fruitTypes);
        fruit1Combos[levelIndex].setFont(new Font("Arial", Font.BOLD, 13));
        fruit1Combos[levelIndex].setBounds(comboX, startY, comboWidth, comboHeight);
        add(fruit1Combos[levelIndex]);

        // Fruit 2
        fruit2Combos[levelIndex] = new JComboBox<>(fruitTypes);
        fruit2Combos[levelIndex].setSelectedIndex(1);
        fruit2Combos[levelIndex].setFont(new Font("Arial", Font.BOLD, 13));
        fruit2Combos[levelIndex].setBounds(comboX, startY + spacing, comboWidth, comboHeight);
        add(fruit2Combos[levelIndex]);

        // Enemy
        enemyCombos[levelIndex] = new JComboBox<>(enemyTypes);
        enemyCombos[levelIndex].setFont(new Font("Arial", Font.BOLD, 13));
        enemyCombos[levelIndex].setBounds(comboX, startY + spacing * 2, comboWidth, comboHeight);
        add(enemyCombos[levelIndex]);

        // Obstacles
        obstacleCombos[levelIndex] = new JComboBox<>(obstacleTypes);
        obstacleCombos[levelIndex].setFont(new Font("Arial", Font.BOLD, 13));
        obstacleCombos[levelIndex].setBounds(comboX, startY + spacing * 3, comboWidth, comboHeight);
        add(obstacleCombos[levelIndex]);
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
        // Botón START
        if (x >= 300 && x <= 500 && y >= 595 && y <= 645) {
            startGame();
        }
        
        // Botón BACK
        if (x >= 100 && x <= 300 && y >= 595 && y <= 645) {
            mainWindow.returnToMenu();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Fondo principal
        if (bg != null) {
            g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        }
        
        // Título principal
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        drawText(g2d, "CONFIGURE LEVELS", 116, 50, Color.BLACK, new Color(135, 206, 250), 3);
        
        // NIVEL 1
        drawLevelPanel(g2d, 1, 90);
        
        // NIVEL 2
        drawLevelPanel(g2d, 2, 255);
        
        // NIVEL 3
        drawLevelPanel(g2d, 3, 420);
        
        // Botones
        drawButton(g2d, 100, 595, 200, 50, "BACK");
        drawButton(g2d, 300, 595, 200, 50, "START GAME");
    }
    
    private void drawLevelPanel(Graphics2D g2d, int levelNum, int yPos) {
        int panelX = 50;
        int panelWidth = 500;
        int panelHeight = 150;
        
        // Fondo del panel
        if (bgPattern != null) {
            g2d.drawImage(bgPattern, panelX, yPos, panelWidth, panelHeight, null);
        }
        
        // Borde negro delgado
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(panelX, yPos, panelWidth, panelHeight);
        
        // Título del nivel
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        drawText(g2d, "LEVEL " + levelNum, panelX + 12, yPos + 30, Color.BLACK, new Color(135, 206, 250), 2);
        
        // Labels de las opciones
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        int labelX = panelX + 127;
        int startY = yPos + 31;
        int spacing = 35;
        
        drawText(g2d, "Fruit 1:", labelX, startY, Color.BLACK, Color.WHITE, 1);
        drawText(g2d, "Fruit 2:", labelX, startY + spacing, Color.BLACK, Color.WHITE, 1);
        drawText(g2d, "Enemy:", labelX, startY + spacing * 2, Color.BLACK, Color.WHITE, 1);
        drawText(g2d, "Obstacles:", labelX, startY + spacing * 3, Color.BLACK, Color.WHITE, 1);
    }
    
    private void drawButton(Graphics2D g2d, int x, int y, int width, int height, String text) {
        if (buttonStart != null) {
            g2d.drawImage(buttonStart, x, y, width, height, null);
        } else {
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillRoundRect(x, y, width, height, 20, 20);
        }
        
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
        
        drawText(g2d, text, textX, textY, Color.BLACK, Color.YELLOW, 2);
    }
    
    private void drawText(Graphics2D g2d, String text, int x, int y, Color outline, Color fill, int thickness) {
        g2d.setColor(outline);
        for (int dx = -thickness; dx <= thickness; dx++) {
            for (int dy = -thickness; dy <= thickness; dy++) {
                if (dx != 0 || dy != 0) {
                    g2d.drawString(text, x + dx, y + dy);
                }
            }
        }
        g2d.setColor(fill);
        g2d.drawString(text, x, y);
    }

    private void startGame(){
    	try {
	        for (int i = 0; i < 3; i++) {
	            String fruit1 = (String) fruit1Combos[i].getSelectedItem();
	            String fruit2 = (String) fruit2Combos[i].getSelectedItem();
	            String enemy = (String) enemyCombos[i].getSelectedItem();
	            String obstacle = (String) obstacleCombos[i].getSelectedItem();
	
	            if (fruit1.equals(fruit2)) {
	                throw new BadDopoCreamExceptions(
	                    BadDopoCreamExceptions.DUPLICATE_FRUIT + 
	                    " en Level " + (i + 1)
	                );
	            }
	
	            levelConfigs[i] = new LevelConfiguration(fruit1, fruit2, enemy, obstacle);
	        }
	
	        mainWindow.startGameWithConfig(gameMode, levelConfigs);
    	} catch (BadDopoCreamExceptions e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(),
                "Configuration Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    	}
}