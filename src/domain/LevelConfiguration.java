package domain;

import java.io.Serializable;

/**
 * Almacena la configuración de un nivel
 * (frutas, enemigos y obstáculos seleccionados por el usuario)
 */
public class LevelConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String fruit1Type;
    private String fruit2Type;
    private String enemyType;
    private String obstacleType;
    
    /**
     * Constructor
     * @param fruit1Type Tipo de la primera fruta
     * @param fruit2Type Tipo de la segunda fruta
     * @param enemyType Tipo de enemigo
     * @param obstacleType Tipo de obstáculo
     */
    public LevelConfiguration(String fruit1Type, String fruit2Type, String enemyType, String obstacleType) {
        this.fruit1Type = fruit1Type;
        this.fruit2Type = fruit2Type;
        this.enemyType = enemyType;
        this.obstacleType = obstacleType;
    }
    
    // Getters
    public String getFruit1Type() {
        return fruit1Type;
    }
    
    public String getFruit2Type() {
        return fruit2Type;
    }
    
    public String getEnemyType() {
        return enemyType;
    }
    
    public String getObstacleType() {
        return obstacleType;
    }
    
    // Setters
    public void setFruit1Type(String type) {
        this.fruit1Type = type;
    }
    
    public void setFruit2Type(String type) {
        this.fruit2Type = type;
    }
    
    public void setEnemyType(String type) {
        this.enemyType = type;
    }
    
    public void setObstacleType(String type) {
        this.obstacleType = type;
    }
    
    @Override
    public String toString() {
        return "LevelConfiguration{" +
               "fruit1=" + fruit1Type +
               ", fruit2=" + fruit2Type +
               ", enemy=" + enemyType +
               ", obstacle=" + obstacleType +
               '}';
    }
}