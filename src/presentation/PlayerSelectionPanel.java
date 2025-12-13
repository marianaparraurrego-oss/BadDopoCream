package presentation;

import domain.LevelConfiguration;
import javax.swing.*;

import exceptions.BadDopoCreamExceptions;

import java.awt.*;
import java.awt.event.*;

public class PlayerSelectionPanel extends JPanel {
    private MainWindow mainWindow;
    private String gameMode;
    private LevelConfiguration[] levelConfigs;
    private Color selectedColorP1 = Color.WHITE;
    private Color selectedColorP2 = Color.PINK;
    
    private String selectedAIProfile1 = "expert";
    private String selectedAIProfile2 = "expert";
    private String[] aiProfiles = {"hungry", "fearful", "expert"};
    
    private Image bg;
    private Image bgPattern;
    private Image buttonStart;
    private Image iceVainilla, iceFresa, iceChocolate;
    
    // Variables para posición de ComboBox
    private int comboBoxY1 = 250;
    private int comboBoxY2 = 410;
    
    public PlayerSelectionPanel(MainWindow mainWindow, String gameMode, LevelConfiguration[] levelConfigs) {
        this.mainWindow = mainWindow;
        this.gameMode = gameMode;
        this.levelConfigs = levelConfigs;
        loadImage();
        prepareElements();
        prepareActions();
    }
    
    private void loadImage() {

            bg = new ImageIcon(getClass().getResource("/presentation/imagenes/fondo principal.png")).getImage();
            bgPattern = new ImageIcon(getClass().getResource("/presentation/imagenes/FONDO.png")).getImage();
            buttonStart = new ImageIcon(getClass().getResource("/presentation/imagenes/boton.png")).getImage();
            iceVainilla = new ImageIcon(getClass().getResource("/presentation/imagenes/vainillacream.png")).getImage();
            iceFresa = new ImageIcon(getClass().getResource("/presentation/imagenes/fresacream.png")).getImage();
            iceChocolate = new ImageIcon(getClass().getResource("/presentation/imagenes/chocolatecream.png")).getImage();
    }
    
    private void prepareElements() {
        setLayout(null); // Layout absoluto para combinar pintado y componentes
        
        // Ajustar tamaño según modo
        int height = 550;
        if (gameMode.equals("PvM")) height = 600;
        if (gameMode.equals("MvM")) height = 650;
        
        setPreferredSize(new Dimension(600, height));
        
        // Crear ComboBox para PvM
        if (gameMode.equals("PvM")) {
            JComboBox<String> aiProfile2Combo = new JComboBox<>(aiProfiles);
            aiProfile2Combo.setFont(new Font("Arial", Font.BOLD, 14));
            aiProfile2Combo.setBounds(240, 415, 160, 30);
            aiProfile2Combo.setSelectedItem(selectedAIProfile2);
            aiProfile2Combo.addActionListener(e -> {
                selectedAIProfile2 = (String) aiProfile2Combo.getSelectedItem();
            });
            add(aiProfile2Combo);
        }
        
        // Crear ComboBox para MvM
        if (gameMode.equals("MvM")) {
            JComboBox<String> aiProfile1Combo = new JComboBox<>(aiProfiles);
            aiProfile1Combo.setFont(new Font("Arial", Font.BOLD, 14));
            aiProfile1Combo.setBounds(248, 260, 160, 30);
            aiProfile1Combo.setSelectedItem(selectedAIProfile1);
            aiProfile1Combo.addActionListener(e -> {
                selectedAIProfile1 = (String) aiProfile1Combo.getSelectedItem();
            });
            add(aiProfile1Combo);
            
            JComboBox<String> aiProfile2Combo = new JComboBox<>(aiProfiles);
            aiProfile2Combo.setFont(new Font("Arial", Font.BOLD, 14));
            aiProfile2Combo.setBounds(248, 510, 160, 30);
            aiProfile2Combo.setSelectedItem(selectedAIProfile2);
            aiProfile2Combo.addActionListener(e -> {
                selectedAIProfile2 = (String) aiProfile2Combo.getSelectedItem();
            });
            add(aiProfile2Combo);
        }
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
        if (gameMode.equals("PvP")) {
            // PLAYER 1
            if (x >= 140 && x <= 220 && y >= 160 && y <= 240) {
                selectedColorP1 = Color.WHITE;
            }
            if (x >= 260 && x <= 340 && y >= 160 && y <= 240) {
                selectedColorP1 = Color.PINK;
            }
            if (x >= 380 && x <= 460 && y >= 160 && y <= 240) {
                selectedColorP1 = new Color(139, 69, 19);
            }
            
            // PLAYER 2
            if (x >= 140 && x <= 220 && y >= 320 && y <= 400) {
                selectedColorP2 = Color.WHITE;
            }
            if (x >= 260 && x <= 340 && y >= 320 && y <= 400) {
                selectedColorP2 = Color.PINK;
            }
            if (x >= 380 && x <= 460 && y >= 320 && y <= 400) {
                selectedColorP2 = new Color(139, 69, 19);
            }
            
            // BOTÓN START
            if (x >= 200 && x <= 400 && y >= 450 && y <= 500) {
                startGame();
            }
            
        } else if (gameMode.equals("PvM")) {
            // PLAYER 1 (HUMANO)
            if (x >= 140 && x <= 220 && y >= 160 && y <= 240) {
                selectedColorP1 = Color.WHITE;
            }
            if (x >= 260 && x <= 340 && y >= 160 && y <= 240) {
                selectedColorP1 = Color.PINK;
            }
            if (x >= 380 && x <= 460 && y >= 160 && y <= 240) {
                selectedColorP1 = new Color(139, 69, 19);
            }
            
            // PLAYER 2 (IA)
            if (x >= 140 && x <= 220 && y >= 320 && y <= 400) {
                selectedColorP2 = Color.WHITE;
            }
            if (x >= 260 && x <= 340 && y >= 320 && y <= 400) {
                selectedColorP2 = Color.PINK;
            }
            if (x >= 380 && x <= 460 && y >= 320 && y <= 400) {
                selectedColorP2 = new Color(139, 69, 19);
            }
            
            // BOTÓN START
            if (x >= 200 && x <= 400 && y >= 500 && y <= 550) {
                startGame();
            }
            
        } else if (gameMode.equals("MvM")) {
            // AI PLAYER 1
            if (x >= 140 && x <= 220 && y >= 160 && y <= 240) {
                selectedColorP1 = Color.WHITE;
            }
            if (x >= 260 && x <= 340 && y >= 160 && y <= 240) {
                selectedColorP1 = Color.PINK;
            }
            if (x >= 380 && x <= 460 && y >= 160 && y <= 240) {
                selectedColorP1 = new Color(139, 69, 19);
            }
            
            // AI PLAYER 2
            if (x >= 140 && x <= 220 && y >= 420 && y <= 500) {
                selectedColorP2 = Color.WHITE;
            }
            if (x >= 260 && x <= 340 && y >= 420 && y <= 500) {
                selectedColorP2 = Color.PINK;
            }
            if (x >= 380 && x <= 460 && y >= 420 && y <= 500) {
                selectedColorP2 = new Color(139, 69, 19);
            }
            
            // BOTÓN START
            if (x >= 200 && x <= 400 && y >= 560 && y <= 610) {
                startGame();
            }
            
        } else if (gameMode.equals("Player")) {
            // SINGLE PLAYER
            if (x >= 140 && x <= 220 && y >= 200 && y <= 280) {
                selectedColorP1 = Color.WHITE;
                startGame();
            }
            if (x >= 260 && x <= 340 && y >= 200 && y <= 280) {
                selectedColorP1 = Color.PINK;
                startGame();
            }
            if (x >= 380 && x <= 460 && y >= 200 && y <= 280) {
                selectedColorP1 = new Color(139, 69, 19);
                startGame();
            }
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Fondo
        g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        
        // Dibujar según modo
        if (gameMode.equals("PvP")) {
            drawPvPSelection(g2d);
        } else if (gameMode.equals("PvM")) {
            drawPvMSelection(g2d);
        } else if (gameMode.equals("MvM")) {
            drawMvMSelection(g2d);
        } else if (gameMode.equals("Player")) {
            drawSinglePlayerSelection(g2d);
        }
    }
    
    // Método para dibujar marco decorativo dorado
    private void drawDecorativeBorder(Graphics2D g2d, int x, int y, int width, int height) {
        Color darkGold = new Color(139, 90, 0);
        Color gold = new Color(218, 165, 32);
        Color lightGold = new Color(255, 215, 0);
        
        // Borde exterior oscuro
        g2d.setColor(darkGold);
        g2d.setStroke(new BasicStroke(8));
        g2d.drawRoundRect(x - 4, y - 4, width + 8, height + 8, 15, 15);
        
        // Borde medio dorado
        g2d.setColor(gold);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x - 2, y - 2, width + 4, height + 4, 15, 15);
        
        // Borde interior claro
        g2d.setColor(lightGold);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, width, height, 15, 15);
        
        // Esquinas decorativas
        int cornerSize = 12;
        int[][] corners = {
            {x - 8, y - 8},
            {x + width - 4, y - 8},
            {x - 8, y + height - 4},
            {x + width - 4, y + height - 4}
        };
        
        for (int[] corner : corners) {
            g2d.setColor(darkGold);
            g2d.fillOval(corner[0], corner[1], cornerSize, cornerSize);
            g2d.setColor(lightGold);
            g2d.fillOval(corner[0] + 3, corner[1] + 3, cornerSize - 6, cornerSize - 6);
        }
    }
    
    private void drawPvPSelection(Graphics2D g2d) {
        
    	int panelX = 50;
        int panelY = 100;
        int panelWidth = 500;
        int panelHeight = 340;
        
        if (bgPattern != null) {
            g2d.drawImage(bgPattern, panelX, panelY, panelWidth, panelHeight, null);
        }
        
        drawDecorativeBorder(g2d, panelX, panelY, panelWidth, panelHeight);
    	
    	// Título
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        drawText(g2d, "Player vs Player", 160, 60, Color.BLACK, new Color(135, 206, 250), 3);
        
        // Player 1
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        drawText(g2d, "Player 1 - Choose Color", 160, 130, Color.BLACK, new Color(100, 150, 255), 2);
        
        drawOption(g2d, 140, 160, iceVainilla, selectedColorP1.equals(Color.WHITE));
        drawOption(g2d, 260, 160, iceFresa, selectedColorP1.equals(Color.PINK));
        drawOption(g2d, 380, 160, iceChocolate, selectedColorP1.equals(new Color(139, 69, 19)));
        
        // Separador
        g2d.setColor(new Color(139, 90, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(80, 265, 520, 265);
        g2d.setColor(new Color(255, 215, 0));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(80, 266, 520, 266);
        
        // Player 2
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        drawText(g2d, "Player 2 - Choose Color", 160, 300, Color.BLACK, new Color(255, 100, 150), 2);
        
        drawOption(g2d, 140, 320, iceVainilla, selectedColorP2.equals(Color.WHITE));
        drawOption(g2d, 260, 320, iceFresa, selectedColorP2.equals(Color.PINK));
        drawOption(g2d, 380, 320, iceChocolate, selectedColorP2.equals(new Color(139, 69, 19)));
        
        // Botón START
        drawStartButton(g2d, 200, 450, 200, 50);
        
        // Controles
        g2d.setFont(new Font("Arial", Font.ITALIC, 12));
        drawText(g2d, "P1: Arrows + Space  |  P2: WASD + Shift", 185, 425, Color.BLACK, Color.WHITE, 1);
    }
    
    private void drawPvMSelection(Graphics2D g2d) {
        
    	int panelX = 50;
        int panelY = 100;
        int panelWidth = 500;
        int panelHeight = 350;
        
        if (bgPattern != null) {
            g2d.drawImage(bgPattern, panelX, panelY, panelWidth, panelHeight, null);
        }
        
        drawDecorativeBorder(g2d, panelX, panelY, panelWidth, panelHeight);
        
    	
    	// Título
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        drawText(g2d, "Player vs Machine", 145, 60, Color.BLACK, new Color(135, 206, 250), 3);
        
        // Player 1 (Humano)
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        drawText(g2d, "Player 1 (Human) - Choose Color", 135, 130, Color.BLACK, new Color(100, 150, 255), 2);
        
        drawOption(g2d, 140, 160, iceVainilla, selectedColorP1.equals(Color.WHITE));
        drawOption(g2d, 260, 160, iceFresa, selectedColorP1.equals(Color.PINK));
        drawOption(g2d, 380, 160, iceChocolate, selectedColorP1.equals(new Color(139, 69, 19)));
        
        // Separador
        g2d.setColor(new Color(139, 90, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(80, 265, 520, 265);
        g2d.setColor(new Color(255, 215, 0));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(80, 266, 520, 266);
        
        // Player 2 (IA)
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        drawText(g2d, "Player 2 (AI) - Configure", 175, 300, Color.BLACK, new Color(255, 100, 150), 2);
        
        drawOption(g2d, 140, 320, iceVainilla, selectedColorP2.equals(Color.WHITE));
        drawOption(g2d, 260, 320, iceFresa, selectedColorP2.equals(Color.PINK));
        drawOption(g2d, 380, 320, iceChocolate, selectedColorP2.equals(new Color(139, 69, 19)));
        
        // Label para ComboBox
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        drawText(g2d, "AI Profile:", 160, 435, Color.BLACK, Color.WHITE, 1);
        
        // Botón START
        drawStartButton(g2d, 200, 500, 200, 50);
    }
    
    private void drawMvMSelection(Graphics2D g2d) {
        
    	int panelX = 50;
        int panelY = 100;
        int panelWidth = 500;
        int panelHeight = 450;
        
        if (bgPattern != null) {
            g2d.drawImage(bgPattern, panelX, panelY, panelWidth, panelHeight, null);
        }
        
        drawDecorativeBorder(g2d, panelX, panelY, panelWidth, panelHeight);
    	
    	// Título
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        drawText(g2d, "Machine vs Machine", 135, 60, Color.BLACK, new Color(135, 206, 250), 3);
        
        // AI Player 1
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        drawText(g2d, "AI Player 1 - Configure", 180, 130, Color.BLACK, new Color(100, 150, 255), 2);
        
        drawOption(g2d, 140, 160, iceVainilla, selectedColorP1.equals(Color.WHITE));
        drawOption(g2d, 260, 160, iceFresa, selectedColorP1.equals(Color.PINK));
        drawOption(g2d, 380, 160, iceChocolate, selectedColorP1.equals(new Color(139, 69, 19)));
        
        // Label para ComboBox AI 1
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        drawText(g2d, "AI Profile:", 165, 278, Color.BLACK, Color.WHITE, 1);
        
        // Separador
        g2d.setColor(new Color(139, 90, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(80, 325, 520, 325);
        g2d.setColor(new Color(255, 215, 0));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(80, 326, 520, 326);
        
        // AI Player 2
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        drawText(g2d, "AI Player 2 - Configure", 180, 379, Color.BLACK, new Color(255, 100, 150), 2);
        
        drawOption(g2d, 140, 400, iceVainilla, selectedColorP2.equals(Color.WHITE));
        drawOption(g2d, 260, 400, iceFresa, selectedColorP2.equals(Color.PINK));
        drawOption(g2d, 380, 400, iceChocolate, selectedColorP2.equals(new Color(139, 69, 19)));
        
        // Label para ComboBox AI 2
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        drawText(g2d, "AI Profile:", 165, 530, Color.BLACK, Color.WHITE, 1);
        
        // Botón START
        drawStartButton(g2d, 200, 560, 200, 50);
    }
    
    private void drawSinglePlayerSelection(Graphics2D g2d) {
       
    	int panelX = 80;
        int panelY = 150;
        int panelWidth = 440;
        int panelHeight = 200;
        
        if (bgPattern != null) {
            g2d.drawImage(bgPattern, panelX, panelY, panelWidth, panelHeight, null);
        }
        
        drawDecorativeBorder(g2d, panelX, panelY, panelWidth, panelHeight);
    	
    	// Título
        g2d.setFont(new Font("Arial", Font.BOLD, 38));
        drawText(g2d, "CHOOSE YOUR FLAVOUR", 60, 100, Color.BLACK, new Color(135, 206, 250), 3);     
        
        // Opciones
        drawOption(g2d, 140, 200, iceVainilla, false);
        drawOption(g2d, 260, 200, iceFresa, false);
        drawOption(g2d, 380, 200, iceChocolate, false);
        
        // Etiquetas
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        drawText(g2d, "Vanilla", 155, 300, Color.BLACK, Color.WHITE, 1);
        drawText(g2d, "Strawberry", 260, 300, Color.BLACK, new Color(255, 150, 200), 1);
        drawText(g2d, "Chocolate", 385, 300, Color.BLACK, new Color(139, 69, 19), 1);
        
        // Instrucción
        g2d.setFont(new Font("Arial", Font.ITALIC, 20));
        drawText(g2d, "Click on your favorite ice cream!", 160, 390, Color.BLACK, Color.YELLOW, 1);
    }
    
    private void drawOption(Graphics2D g2d, int x, int y, Image img, boolean selected) {
        g2d.drawImage(img, x, y, 80, 80, null);
        
        if (selected) {
            g2d.setColor(new Color(255, 215, 0));
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRect(x - 3, y - 3, 86, 86);
        }
    }
    
    private void drawStartButton(Graphics2D g2d, int x, int y, int width, int height) {
        if (buttonStart != null) {
            g2d.drawImage(buttonStart, x, y, width, height, null);
        } else {
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillRoundRect(x, y, width, height, 20, 20);
        }
        
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "START GAME";
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
    
    private void startGame() {
    	 try {
             // EXCEPCIÓN: Verificar colores duplicados en PvP y PvM
             if ((gameMode.equals("PvP") || gameMode.equals("PvM") || gameMode.equals("MvM")) && 
                 selectedColorP1.equals(selectedColorP2)) {
                 throw new BadDopoCreamExceptions(BadDopoCreamExceptions.DUPLICATE_ICECREAM);
             }

             String aiProfile1 = null;
             String aiProfile2 = null;
             
             switch(gameMode) {
                 case "PvM":
                     aiProfile1 = null;
                     aiProfile2 = selectedAIProfile2;
                     break;
                 case "MvM":
                     aiProfile1 = selectedAIProfile1;
                     aiProfile2 = selectedAIProfile2;
                     break;
                 default:
                     aiProfile1 = null;
                     aiProfile2 = null;
                     break;
             }
             
             mainWindow.startGameWithColor(1, gameMode, selectedColorP1, selectedColorP2, 
                                          levelConfigs, aiProfile1, aiProfile2);

         } catch (BadDopoCreamExceptions e) {
             JOptionPane.showMessageDialog(this, 
                 e.getMessage(),
                 "Selection Error", 
                 JOptionPane.WARNING_MESSAGE);
         }
    }
}