package domain;

import java.awt.Color;
import java.util.List;

public class ExpertAI extends IceCreamAI {
    
    public ExpertAI(int gridX, int gridY, Color color) {
        super(gridX, gridY, color, "expert");
    }
    
    @Override
    public void makeDecision() {
        if (board == null) return;
        
        // 1. Evaluar amenazas inmediatas
        int threatLevel = evaluateThreatLevel();
        
        if (threatLevel > 7) { // Peligro alto
            handleHighThreat();
        } else if (threatLevel > 3) { // Peligro medio
            handleMediumThreat();
        } else { // Peligro bajo
            handleLowThreat();
        }
        
        // Ocasionalmente disparar estratégicamente
        shootIfPossible();
    }
    
    private int evaluateThreatLevel() {
        int threatLevel = 0;
        List<Enemy> enemies = board.getEnemies();
        
        for (Enemy enemy : enemies) {
            int distance = Math.abs(enemy.getGridX() - gridX) + 
                          Math.abs(enemy.getGridY() - gridY);
            
            if (distance <= 2) threatLevel += 5;
            else if (distance <= 4) threatLevel += 3;
            else if (distance <= 6) threatLevel += 1;
            
            // Enemigos especiales son más peligrosos
            if (enemy instanceof Narval) threatLevel += 2;
            if (enemy instanceof OrangeSquid) threatLevel += 1;
        }
        
        // Verificar cactus con púas cercanos
        for (Fruit fruit : board.getFruits()) {
            if (fruit instanceof Cactus) {
                Cactus cactus = (Cactus) fruit;
                if (cactus.hasSpikes()) {
                    int distance = Math.abs(cactus.getGridX() - gridX) + 
                                  Math.abs(cactus.getGridY() - gridY);
                    if (distance <= 2) threatLevel += 3;
                }
            }
        }
        
        return threatLevel;
    }
    
    
    private void handleHighThreat() {
        // En peligro alto: priorizar supervivencia
        Enemy nearestEnemy = findNearestEnemy();
        if (nearestEnemy != null) {
            moveAwayFrom(nearestEnemy.getGridX(), nearestEnemy.getGridY());
            
            // Intentar crear barreras entre el enemigo y la IA
            int dx = nearestEnemy.getGridX() - gridX;
            int dy = nearestEnemy.getGridY() - gridY;
            
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) direction = LEFT;
                else direction = RIGHT;
            } else {
                if (dy > 0) direction = UP;
                else direction = DOWN;
            }
            
            shootIce(); // Crear barrera de hielo
        }
    }
    
    private void handleMediumThreat() {
        // En peligro medio: buscar frutas evitando peligros
        Fruit bestFruit = findBestStrategicFruit();
        
        if (bestFruit != null) {
            moveTowards(bestFruit.getGridX(), bestFruit.getGridY());
        } else {
            // Moverse hacia área segura
            moveToSafeArea();
        }
    }
    
    private void handleLowThreat() {
        // En peligro bajo: recolectar frutas agresivamente
        Fruit nearestFruit = findNearestFruit();
        
        if (nearestFruit != null) {
            moveTowards(nearestFruit.getGridX(), nearestFruit.getGridY());
            
            // Si la fruta es valiosa y está cerca, priorizarla
            if (nearestFruit.getPoints() >= 150) {
                // Moverse más directamente hacia frutas valiosas
                int dx = nearestFruit.getGridX() - gridX;
                int dy = nearestFruit.getGridY() - gridY;
                
                if (Math.abs(dx) > 0 && board.canMoveTo(gridX + (dx > 0 ? 1 : -1), gridY)) {
                    move(dx > 0 ? RIGHT : LEFT);
                } else if (Math.abs(dy) > 0 && board.canMoveTo(gridX, gridY + (dy > 0 ? 1 : -1))) {
                    move(dy > 0 ? DOWN : UP);
                }
            }
        } else {
            // Explorar áreas no visitadas
            explore();
        }
    }
    
    private Fruit findBestStrategicFruit() {
        List<Fruit> fruits = board.getFruits();
        if (fruits.isEmpty()) return null;
        
        Fruit bestFruit = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        
        for (Fruit fruit : fruits) {
            if (!fruit.isVisibleFruit()) continue;
            
            if (fruit instanceof Cactus) {
                Cactus cactus = (Cactus) fruit;
                if (cactus.hasSpikes()) continue;
            }
            
            double score = calculateFruitStrategicScore(fruit);
            if (score > bestScore) {
                bestScore = score;
                bestFruit = fruit;
            }
        }
        
        return bestFruit;
    }
    
    private double calculateFruitStrategicScore(Fruit fruit) {
        int distance = Math.abs(fruit.getGridX() - gridX) + 
                      Math.abs(fruit.getGridY() - gridY);
        
        // Puntuación basada en valor y seguridad
        double score = fruit.getPoints() / (distance + 1.0);
        
        // Bonus por seguridad
        if (isPositionSafe(fruit.getGridX(), fruit.getGridY())) {
            score *= 1.5;
        }
        
        // Penalización por enemigos cercanos a la fruta
        int nearbyEnemies = 0;
        for (Enemy enemy : board.getEnemies()) {
            int enemyDistance = Math.abs(enemy.getGridX() - fruit.getGridX()) + 
                               Math.abs(enemy.getGridY() - fruit.getGridY());
            if (enemyDistance <= 3) nearbyEnemies++;
        }
        score -= nearbyEnemies * 10;
        
        return score;
    }
    
    private void moveToSafeArea() {
        // Buscar la posición más segura dentro de un radio
        int bestX = gridX;
        int bestY = gridY;
        int bestSafety = calculatePositionSafety(gridX, gridY);
        
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                int newX = gridX + dx;
                int newY = gridY + dy;
                
                if (board.canMoveTo(newX, newY)) {
                    int safety = calculatePositionSafety(newX, newY);
                    if (safety > bestSafety) {
                        bestSafety = safety;
                        bestX = newX;
                        bestY = newY;
                    }
                }
            }
        }
        
        moveTowards(bestX, bestY);
    }
    
    private int calculatePositionSafety(int x, int y) {
        int safety = 0;
        
        // Mayor seguridad cuanto más lejos de enemigos
        for (Enemy enemy : board.getEnemies()) {
            int distance = Math.abs(enemy.getGridX() - x) + 
                          Math.abs(enemy.getGridY() - y);
            safety += distance; // Mayor distancia = más seguro
        }
        
        // Penalizar posiciones peligrosas
        for (Bonfire bonfire : board.getBonfires()) {
            if (bonfire.isActive() && bonfire.getGridX() == x && bonfire.getGridY() == y) {
                safety -= 100;
            }
        }
        
        for (Fruit fruit : board.getFruits()) {
            if (fruit instanceof Cactus) {
                Cactus cactus = (Cactus) fruit;
                if (cactus.hasSpikes() && cactus.getGridX() == x && cactus.getGridY() == y) {
                    safety -= 50;
                }
            }
        }
        
        return safety;
    }
    
    private void explore() {
        // Moverse hacia áreas menos visitadas o bordes
        int direction = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            int dir = (direction + i) % 4;
            if (tryMove(dir)) return;
        }
    }
    
    
}