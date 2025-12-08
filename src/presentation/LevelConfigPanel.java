package presentation;

import domain.LevelConfiguration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel para configurar los niveles del juego
 * Permite al usuario seleccionar frutas y enemigos para cada nivel
 */
public class LevelConfigPanel extends JPanel {
    private MainWindow mainWindow;
    private String gameMode;
    private LevelConfiguration[] levelConfigs;
    
    private JComboBox<String>[] fruit1Combos;
    private JComboBox<String>[] fruit2Combos;
    private JComboBox<String>[] enemyCombos;
    private JComboBox<String>[] obstacleCombos;
    
    private String[] fruitTypes = {"Grapes", "Banana", "Cherry", "Pineapple", "Cactus"};
    private String[] enemyTypes = {"Troll", "Pot", "OrangeSquid", "Narval"};
    private String[] obstacleTypes = {"Bonfire", "HotTile", "Both"};
    
    @SuppressWarnings("unchecked")
    public LevelConfigPanel(MainWindow mainWindow, String gameMode) {
        this.mainWindow = mainWindow;
        this.gameMode = gameMode;
        this.levelConfigs = new LevelConfiguration[3];
        
        // Inicializar arrays
        fruit1Combos = new JComboBox[3];
        fruit2Combos = new JComboBox[3];
        enemyCombos = new JComboBox[3];
        obstacleCombos = new JComboBox[3];
        
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 220, 255));
        setPreferredSize(new Dimension(700, 600));
        
        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(200, 220, 255));
        JLabel titleLabel = new JLabel("CONFIGURE LEVELS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 100, 200));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel central con configuraciones
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(200, 220, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Crear paneles para cada nivel
        for(int i = 0; i < 3; i++) {
            centerPanel.add(createLevelPanel(i + 1));
            centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(200, 220, 255));
        
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setBackground(new Color(50, 200, 50));
        startButton.addActionListener(e -> startGame());
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> mainWindow.returnToMenu());
        
        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel de configuración para un nivel
     */
    private JPanel createLevelPanel(int levelNum) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 255), 3),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Título del nivel
        JLabel levelLabel = new JLabel("LEVEL " + levelNum);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setForeground(new Color(50, 100, 200));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(levelLabel);
        panel.add(titlePanel, BorderLayout.NORTH);
        
        // Grid con las opciones
        JPanel gridPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        gridPanel.setBackground(Color.WHITE);
        
        int idx = levelNum - 1;
        
        // Fruta 1
        JLabel fruit1Label = new JLabel("Fruit 1:");
        fruit1Label.setFont(new Font("Arial", Font.BOLD, 14));
        fruit1Combos[idx] = new JComboBox<>(fruitTypes);
        fruit1Combos[idx].setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Fruta 2
        JLabel fruit2Label = new JLabel("Fruit 2:");
        fruit2Label.setFont(new Font("Arial", Font.BOLD, 14));
        fruit2Combos[idx] = new JComboBox<>(fruitTypes);
        fruit2Combos[idx].setSelectedIndex(1); // Diferente por defecto
        fruit2Combos[idx].setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Enemigo
        JLabel enemyLabel = new JLabel("Enemy:");
        enemyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        enemyCombos[idx] = new JComboBox<>(enemyTypes);
        enemyCombos[idx].setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Obstáculos
        JLabel obstacleLabel = new JLabel("Obstacles:");
        obstacleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        obstacleCombos[idx] = new JComboBox<>(obstacleTypes);
        obstacleCombos[idx].setFont(new Font("Arial", Font.PLAIN, 14));
        
        gridPanel.add(fruit1Label);
        gridPanel.add(fruit1Combos[idx]);
        gridPanel.add(fruit2Label);
        gridPanel.add(fruit2Combos[idx]);
        gridPanel.add(enemyLabel);
        gridPanel.add(enemyCombos[idx]);
        gridPanel.add(obstacleLabel);
        gridPanel.add(obstacleCombos[idx]);
        
        panel.add(gridPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void prepareActions() {
        // Nada específico por ahora
    }
    
    /**
     * Valida y inicia el juego con las configuraciones
     */
    private void startGame() {
        // Crear configuraciones para cada nivel
        for(int i = 0; i < 3; i++) {
            String fruit1 = (String) fruit1Combos[i].getSelectedItem();
            String fruit2 = (String) fruit2Combos[i].getSelectedItem();
            String enemy = (String) enemyCombos[i].getSelectedItem();
            String obstacle = (String) obstacleCombos[i].getSelectedItem();
            
            // Validar que las frutas sean diferentes
            if(fruit1.equals(fruit2)) {
                JOptionPane.showMessageDialog(this,
                    "Level " + (i + 1) + ": Please select two different fruit types",
                    "Configuration Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            levelConfigs[i] = new LevelConfiguration(fruit1, fruit2, enemy, obstacle);
        }
        
        // Ir a selección de personajes con las configuraciones
        mainWindow.startGameWithConfig(gameMode, levelConfigs);
    }
}