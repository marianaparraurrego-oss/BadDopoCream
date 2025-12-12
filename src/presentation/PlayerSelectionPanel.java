package presentation;

import domain.LevelConfiguration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerSelectionPanel extends JPanel {
    private MainWindow mainWindow;
    private String gameMode;
    private LevelConfiguration[] levelConfigs;
    private Color selectedColorP1 = Color.WHITE;
    private Color selectedColorP2 = Color.PINK;
    
    // Para selección de perfiles de IA
    private String selectedAIProfile1 = "expert";
    private String selectedAIProfile2 = "expert";
    private JComboBox<String> aiProfile1Combo;
    private JComboBox<String> aiProfile2Combo;
    private String[] aiProfiles = {"hungry", "fearful", "expert"};
    
    // Botones de color
    private JButton vanillaBtnP1, strawberryBtnP1, chocolateBtnP1;
    private JButton vanillaBtnP2, strawberryBtnP2, chocolateBtnP2;
    private JButton startButton;
    
    public PlayerSelectionPanel(MainWindow mainWindow, String gameMode, LevelConfiguration[] levelConfigs) {
        this.mainWindow = mainWindow;
        this.gameMode = gameMode;
        this.levelConfigs = levelConfigs;
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements() {
        setBackground(new Color(200, 220, 255));
        setLayout(new BorderLayout());
        
        // Panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(200, 220, 255));
        
        // Título
        JLabel titleLabel = new JLabel(getTitleText(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 100, 200));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Panel de contenido según modo
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel);
        
        // Botón Start
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setPreferredSize(new Dimension(250, 50));
        startButton.setMaximumSize(new Dimension(250, 50));
        startButton.setBackground(new Color(50, 200, 50));
        startButton.setForeground(Color.WHITE);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> startGame());
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(startButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Ajustar tamaño según contenido
        int height = (gameMode.equals("MvM")) ? 650 : 
                    (gameMode.equals("PvM")) ? 600 : 550;
        setPreferredSize(new Dimension(600, height));
    }
    
    private String getTitleText() {
        switch(gameMode) {
            case "PvP": return "Player vs Player";
            case "PvM": return "Player vs Machine";
            case "MvM": return "Machine vs Machine";
            default: return "Choose Your Ice Cream";
        }
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(200, 220, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        
        if (gameMode.equals("PvP")) {
            addPvPContent(panel);
        } else if (gameMode.equals("PvM")) {
            addPvMContent(panel);
        } else if (gameMode.equals("MvM")) {
            addMvMContent(panel);
        } else {
            addSinglePlayerContent(panel);
        }
        
        return panel;
    }
    
    private void addPvPContent(JPanel panel) {
        // Player 1
        JLabel p1Label = new JLabel("Player 1 - Choose Color");
        p1Label.setFont(new Font("Arial", Font.BOLD, 22));
        p1Label.setForeground(new Color(50, 100, 200));
        p1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p1Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createColorButtonsPanel(1));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Separador
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(400, 2));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Player 2
        JLabel p2Label = new JLabel("Player 2 - Choose Color");
        p2Label.setFont(new Font("Arial", Font.BOLD, 22));
        p2Label.setForeground(new Color(200, 50, 100));
        p2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p2Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createColorButtonsPanel(2));
        
        // Controles
        JLabel controlsLabel = new JLabel("Controls: P1 - Arrows + Space | P2 - WASD + Shift");
        controlsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        controlsLabel.setForeground(Color.DARK_GRAY);
        controlsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(controlsLabel);
    }
    
    private void addPvMContent(JPanel panel) {
        // Player 1 (Humano)
        JLabel p1Label = new JLabel("Player 1 (Human) - Choose Color");
        p1Label.setFont(new Font("Arial", Font.BOLD, 22));
        p1Label.setForeground(new Color(50, 100, 200));
        p1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p1Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createColorButtonsPanel(1));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Separador
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(400, 2));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Player 2 (IA)
        JLabel p2Label = new JLabel("Player 2 (AI) - Configure");
        p2Label.setFont(new Font("Arial", Font.BOLD, 22));
        p2Label.setForeground(new Color(200, 50, 100));
        p2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p2Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createColorButtonsPanel(2));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Perfil de IA
        JPanel aiPanel = new JPanel();
        aiPanel.setBackground(new Color(200, 220, 255));
        JLabel aiLabel = new JLabel("AI Profile:");
        aiLabel.setFont(new Font("Arial", Font.BOLD, 16));
        aiProfile2Combo = new JComboBox<>(aiProfiles);
        aiProfile2Combo.setFont(new Font("Arial", Font.PLAIN, 14));
        aiProfile2Combo.setPreferredSize(new Dimension(150, 30));
        aiProfile2Combo.addActionListener(e -> 
            selectedAIProfile2 = (String) aiProfile2Combo.getSelectedItem());
        
        aiPanel.add(aiLabel);
        aiPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        aiPanel.add(aiProfile2Combo);
        aiPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(aiPanel);
    }
    
    private void addMvMContent(JPanel panel) {
        // AI Player 1
        JLabel p1Label = new JLabel("AI Player 1 - Configure");
        p1Label.setFont(new Font("Arial", Font.BOLD, 22));
        p1Label.setForeground(new Color(50, 100, 200));
        p1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p1Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createColorButtonsPanel(1));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Perfil de IA 1
        JPanel ai1Panel = new JPanel();
        ai1Panel.setBackground(new Color(200, 220, 255));
        JLabel ai1Label = new JLabel("AI Profile:");
        ai1Label.setFont(new Font("Arial", Font.BOLD, 16));
        aiProfile1Combo = new JComboBox<>(aiProfiles);
        aiProfile1Combo.setFont(new Font("Arial", Font.PLAIN, 14));
        aiProfile1Combo.setPreferredSize(new Dimension(150, 30));
        aiProfile1Combo.addActionListener(e -> 
            selectedAIProfile1 = (String) aiProfile1Combo.getSelectedItem());
        
        ai1Panel.add(ai1Label);
        ai1Panel.add(Box.createRigidArea(new Dimension(10, 0)));
        ai1Panel.add(aiProfile1Combo);
        ai1Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(ai1Panel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Separador
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(400, 2));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // AI Player 2
        JLabel p2Label = new JLabel("AI Player 2 - Configure");
        p2Label.setFont(new Font("Arial", Font.BOLD, 22));
        p2Label.setForeground(new Color(200, 50, 100));
        p2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(p2Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panel.add(createColorButtonsPanel(2));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Perfil de IA 2
        JPanel ai2Panel = new JPanel();
        ai2Panel.setBackground(new Color(200, 220, 255));
        JLabel ai2Label = new JLabel("AI Profile:");
        ai2Label.setFont(new Font("Arial", Font.BOLD, 16));
        aiProfile2Combo = new JComboBox<>(aiProfiles);
        aiProfile2Combo.setFont(new Font("Arial", Font.PLAIN, 14));
        aiProfile2Combo.setPreferredSize(new Dimension(150, 30));
        aiProfile2Combo.addActionListener(e -> 
            selectedAIProfile2 = (String) aiProfile2Combo.getSelectedItem());
        
        ai2Panel.add(ai2Label);
        ai2Panel.add(Box.createRigidArea(new Dimension(10, 0)));
        ai2Panel.add(aiProfile2Combo);
        ai2Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(ai2Panel);
    }
    
    private void addSinglePlayerContent(JPanel panel) {
        JLabel label = new JLabel("Choose your ice cream");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        panel.add(createColorButtonsPanel(1));
    }
    
    private JPanel createColorButtonsPanel(int playerNumber) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 220, 255));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Espacio inicial
        panel.add(Box.createHorizontalGlue());
        
        // Vanilla
        JButton vanillaBtn = createColorButton(Color.WHITE, "Vanilla", playerNumber);
        panel.add(vanillaBtn);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        // Strawberry
        JButton strawberryBtn = createColorButton(Color.PINK, "Strawberry", playerNumber);
        panel.add(strawberryBtn);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        // Chocolate
        JButton chocolateBtn = createColorButton(new Color(139, 69, 19), "Chocolate", playerNumber);
        panel.add(chocolateBtn);
        
        // Espacio final
        panel.add(Box.createHorizontalGlue());
        
        // Guardar referencias para Player 1
        if (playerNumber == 1) {
            vanillaBtnP1 = vanillaBtn;
            strawberryBtnP1 = strawberryBtn;
            chocolateBtnP1 = chocolateBtn;
        } else {
            vanillaBtnP2 = vanillaBtn;
            strawberryBtnP2 = strawberryBtn;
            chocolateBtnP2 = chocolateBtn;
        }
        
        return panel;
    }
    
    private JButton createColorButton(Color color, String name, int playerNumber) {
        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 120));
        button.setMaximumSize(new Dimension(120, 120));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
        
        // Agregar icono circular
        button.setIcon(createColorIcon(color, 60));
        
        button.addActionListener(e -> {
            if (playerNumber == 1) {
                selectedColorP1 = color;
                updateButtonBorders(1);
            } else {
                selectedColorP2 = color;
                updateButtonBorders(2);
            }
        });
        
        return button;
    }
    
    private Icon createColorIcon(Color color, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillOval(x, y, getIconWidth(), getIconHeight());
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, getIconWidth(), getIconHeight());
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() { return size; }
            
            @Override
            public int getIconHeight() { return size; }
        };
    }
    
    private void updateButtonBorders(int playerNumber) {
        if (playerNumber == 1) {
            vanillaBtnP1.setBorder(selectedColorP1.equals(Color.WHITE) ? 
                BorderFactory.createLineBorder(Color.YELLOW, 4) : 
                BorderFactory.createLineBorder(Color.BLACK, 2));
            strawberryBtnP1.setBorder(selectedColorP1.equals(Color.PINK) ? 
                BorderFactory.createLineBorder(Color.YELLOW, 4) : 
                BorderFactory.createLineBorder(Color.BLACK, 2));
            chocolateBtnP1.setBorder(selectedColorP1.equals(new Color(139, 69, 19)) ? 
                BorderFactory.createLineBorder(Color.YELLOW, 4) : 
                BorderFactory.createLineBorder(Color.BLACK, 2));
        } else {
            vanillaBtnP2.setBorder(selectedColorP2.equals(Color.WHITE) ? 
                BorderFactory.createLineBorder(Color.YELLOW, 4) : 
                BorderFactory.createLineBorder(Color.BLACK, 2));
            strawberryBtnP2.setBorder(selectedColorP2.equals(Color.PINK) ? 
                BorderFactory.createLineBorder(Color.YELLOW, 4) : 
                BorderFactory.createLineBorder(Color.BLACK, 2));
            chocolateBtnP2.setBorder(selectedColorP2.equals(new Color(139, 69, 19)) ? 
                BorderFactory.createLineBorder(Color.YELLOW, 4) : 
                BorderFactory.createLineBorder(Color.BLACK, 2));
        }
    }
    
    private void prepareActions() {
        // Los ActionListeners ya están configurados en los componentes individuales
    }
    
    private void startGame() {
        String aiProfile1 = null;
        String aiProfile2 = null;
        
        // Determinar perfiles según el modo de juego
        switch(gameMode) {
            case "PvM":
                // Player 1 es humano (null), Player 2 es IA
                aiProfile1 = null;
                aiProfile2 = selectedAIProfile2;
                break;
                
            case "MvM":
                // Ambos son IAs
                aiProfile1 = selectedAIProfile1;
                aiProfile2 = selectedAIProfile2;
                break;
                
            case "Player":
            case "PvP":
            default:
                // Sin IA
                aiProfile1 = null;
                aiProfile2 = null;
                break;
        }
        
        // Iniciar el juego con los perfiles seleccionados
        mainWindow.startGameWithColor(1, gameMode, selectedColorP1, selectedColorP2, 
                                     levelConfigs, aiProfile1, aiProfile2);
    }
}