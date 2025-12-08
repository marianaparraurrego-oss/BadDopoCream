package persistence;

import domain.GameState;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la persistencia de partidas
 * Guarda y carga el estado del juego
 */
public class PersistenceManager {
    private static final String SAVE_DIRECTORY = "saves";
    private static final String SAVE_EXTENSION = ".bdc"; // BadDOPOCream
    
    /**
     * Constructor - Crea el directorio de guardado si no existe
     */
    public PersistenceManager() {
        createSaveDirectory();
    }
    
    /**
     * Crea el directorio de guardado
     */
    private void createSaveDirectory() {
        try {
            Path savePath = Paths.get(SAVE_DIRECTORY);
            if(!Files.exists(savePath)) {
                Files.createDirectories(savePath);
            }
        } catch(IOException e) {
            System.err.println("Error creating save directory: " + e.getMessage());
        }
    }
    
    /**
     * Guarda una partida
     * @param state Estado del juego a guardar
     * @param saveName Nombre del archivo de guardado
     * @return true si se guardó exitosamente
     */
    public boolean saveGame(GameState state, String saveName) {
        String fileName = SAVE_DIRECTORY + File.separator + saveName + SAVE_EXTENSION;
        
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(state);
            System.out.println("Game saved successfully: " + fileName);
            return true;
        } catch(IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Carga una partida
     * @param saveName Nombre del archivo a cargar
     * @return El estado del juego cargado, o null si hay error
     */
    public GameState loadGame(String saveName) {
        String fileName = SAVE_DIRECTORY + File.separator + saveName + SAVE_EXTENSION;
        
        try(ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            GameState state = (GameState) ois.readObject();
            System.out.println("Game loaded successfully: " + fileName);
            return state;
        } catch(IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene la lista de partidas guardadas
     * @return Lista con los nombres de las partidas guardadas
     */
    public List<String> getSavedGames() {
        List<String> savedGames = new ArrayList<>();
        
        try {
            Path savePath = Paths.get(SAVE_DIRECTORY);
            
            if(Files.exists(savePath)) {
                DirectoryStream<Path> stream = Files.newDirectoryStream(savePath, "*" + SAVE_EXTENSION);
                
                for(Path entry : stream) {
                    String fileName = entry.getFileName().toString();
                    // Quitar la extensión
                    String gameName = fileName.substring(0, fileName.length() - SAVE_EXTENSION.length());
                    savedGames.add(gameName);
                }
            }
        } catch(IOException e) {
            System.err.println("Error reading saved games: " + e.getMessage());
        }
        
        return savedGames;
    }
    
    /**
     * Elimina una partida guardada
     * @param saveName Nombre de la partida a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean deleteSave(String saveName) {
        String fileName = SAVE_DIRECTORY + File.separator + saveName + SAVE_EXTENSION;
        
        try {
            Path filePath = Paths.get(fileName);
            Files.deleteIfExists(filePath);
            System.out.println("Save deleted: " + fileName);
            return true;
        } catch(IOException e) {
            System.err.println("Error deleting save: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifica si existe una partida guardada
     * @param saveName Nombre de la partida
     * @return true si existe
     */
    public boolean saveExists(String saveName) {
        String fileName = SAVE_DIRECTORY + File.separator + saveName + SAVE_EXTENSION;
        return Files.exists(Paths.get(fileName));
    }
}