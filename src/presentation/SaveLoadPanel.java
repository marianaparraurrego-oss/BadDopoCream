package presentation;

import persistence.PersistenceManager;
import domain.GameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Panel para guardar y cargar partidas
 */
public class SaveLoadPanel extends JPanel {
    private MainWindow mainWindow;
    private PersistenceManager persistenceManager;
    private DefaultListModel<String> saveListModel;
    private JList<String> saveList;
    private JTextField saveNameField;
    private boolean isSaving; // true = guardar, false = cargar
    
    /**
     * Constructor
     * @param mainWindow Ventana principal
     * @param isSaving true para guardar, false para cargar
     */
    public SaveLoadPanel(MainWindow mainWindow, boolean isSaving) {
        this.mainWindow = mainWindow;
        this.isSaving = isSaving;
        this.persistenceManager = new PersistenceManager();
        
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 220, 255));
        setPreferredSize(new Dimension(500, 400));
        
        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(200, 220, 255));
        JLabel titleLabel = new JLabel(isSaving ? "SAVE GAME" : "LOAD GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 100, 200));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel central con lista de guardados
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(200, 220, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Lista de partidas guardadas
        saveListModel = new DefaultListModel<>();
        saveList = new JList<>(saveListModel);
        saveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        saveList.setFont(new Font("Arial", Font.PLAIN, 16));
        saveList.setVisibleRowCount(8);
        
        JScrollPane scrollPane = new JScrollPane(saveList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Campo de texto para nombre (solo al guardar)
        if(isSaving) {
            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            namePanel.setBackground(new Color(200, 220, 255));
            namePanel.add(new JLabel("Save name:"));
            saveNameField = new JTextField(20);
            namePanel.add(saveNameField);
            centerPanel.add(namePanel, BorderLayout.SOUTH);
        }
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(200, 220, 255));
        
        JButton actionButton = new JButton(isSaving ? "Save" : "Load");
        actionButton.setFont(new Font("Arial", Font.BOLD, 16));
        actionButton.setPreferredSize(new Dimension(120, 40));
        actionButton.addActionListener(e -> performAction());
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(e -> deleteSave());
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(e -> mainWindow.returnToMenu());
        
        buttonPanel.add(actionButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Cargar lista de guardados
        refreshSaveList();
    }
    
    private void prepareActions() {
        // Doble clic para cargar/guardar
        saveList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    performAction();
                }
            }
        });
        
        // Seleccionar guardado al hacer clic (para guardar)
        if(isSaving) {
            saveList.addListSelectionListener(e -> {
                if(!e.getValueIsAdjusting()) {
                    String selected = saveList.getSelectedValue();
                    if(selected != null && saveNameField != null) {
                        saveNameField.setText(selected);
                    }
                }
            });
        }
    }
    
    /**
     * Refresca la lista de partidas guardadas
     */
    private void refreshSaveList() {
        saveListModel.clear();
        List<String> savedGames = persistenceManager.getSavedGames();
        for(String saveName : savedGames) {
            saveListModel.addElement(saveName);
        }
    }
    
    /**
     * Ejecuta la acción principal (guardar o cargar)
     */
    private void performAction() {
        if(isSaving) {
            saveGame();
        } else {
            loadGame();
        }
    }
    
    /**
     * Guarda la partida actual
     */
    private void saveGame() {
        String saveName;
        
        if(saveNameField != null && !saveNameField.getText().trim().isEmpty()) {
            saveName = saveNameField.getText().trim();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please enter a save name", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si ya existe
        if(persistenceManager.saveExists(saveName)) {
            int response = JOptionPane.showConfirmDialog(this,
                "A save with this name already exists. Overwrite?",
                "Confirm Overwrite",
                JOptionPane.YES_NO_OPTION);
            
            if(response != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        // Obtener estado del juego
        GameState state = GameState.fromGameController(mainWindow.getGameController());
        
        // Guardar
        if(persistenceManager.saveGame(state, saveName)) {
            JOptionPane.showMessageDialog(this,
                "Game saved successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            refreshSaveList();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error saving game",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carga una partida guardada
     */
    private void loadGame() {
        String selected = saveList.getSelectedValue();
        
        if(selected == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a save to load",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        GameState state = persistenceManager.loadGame(selected);
        
        if(state != null) {
            mainWindow.loadGame(state);
        } else {
            JOptionPane.showMessageDialog(this,
                "Error loading game",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Elimina una partida guardada
     */
    private void deleteSave() {
        String selected = saveList.getSelectedValue();
        
        if(selected == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a save to delete",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int response = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete '" + selected + "'?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if(response == JOptionPane.YES_OPTION) {
            if(persistenceManager.deleteSave(selected)) {
                refreshSaveList();
                JOptionPane.showMessageDialog(this,
                    "Save deleted successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error deleting save",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}