package domain;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public abstract class IceCreamAI extends IceCream {
    protected String profile;
    protected String gameMode; 
    protected Random random;
    
    private int stuckCounter = 0;
    private int lastX = -1;
    private int lastY = -1;
    private int attemptCounter = 0;
    
    public IceCreamAI(int gridX, int gridY, Color color, String profile) {
        super(gridX, gridY, color);
        this.profile = profile;
        this.random = new Random();
    }
    @Override
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public abstract void makeDecision();
    
    public String getProfile() {
        return profile;
    }
    
    protected Fruit findNearestFruit() {
        List<Fruit> fruits = board.getFruits();
        if (fruits.isEmpty()) return null;
        
        Fruit nearest = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Fruit fruit : fruits) {
            if (!fruit.isVisibleFruit()) continue;
            
            if (fruit instanceof Cactus) {
                Cactus cactus = (Cactus) fruit;
                if (cactus.hasSpikes()) continue;
            }
            
            int distance = Math.abs(fruit.getGridX() - gridX) + 
                          Math.abs(fruit.getGridY() - gridY);
            
            if (distance < minDistance) {
                minDistance = distance;
                nearest = fruit;
            }
        }
        
        return nearest;
    }
    
    protected Enemy findNearestEnemy() {
        List<Enemy> enemies = board.getEnemies();
        if (enemies.isEmpty()) return null;
        
        Enemy nearest = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Enemy enemy : enemies) {
            int distance = Math.abs(enemy.getGridX() - gridX) + 
                          Math.abs(enemy.getGridY() - gridY);
            
            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemy;
            }
        }
        
        return nearest;
    }
    
    protected boolean isPositionSafe(int x, int y) {
        // Verificar enemigos cercanos
        for (Enemy enemy : board.getEnemies()) {
            int distance = Math.abs(enemy.getGridX() - x) + 
                          Math.abs(enemy.getGridY() - y);
            if (distance <= 2) return false;
        }
        
        // Verificar cactus con púas
        for (Fruit fruit : board.getFruits()) {
            if (fruit instanceof Cactus) {
                Cactus cactus = (Cactus) fruit;
                if (cactus.hasSpikes() && cactus.getGridX() == x && cactus.getGridY() == y) {
                    return false;
                }
            }
        }
        
        // Verificar fogatas activas
        for (Bonfire bonfire : board.getBonfires()) {
            if (bonfire.isActive() && bonfire.getGridX() == x && bonfire.getGridY() == y) {
                return false;
            }
        }
        
        return true;
    }
    
   
    // 
    public void attemptBreakIce() {
    	 if (board == null) return;
         
    	 int[] directions = {UP, RIGHT, DOWN, LEFT};
         
         for (int dir : directions) {
             int targetX = gridX;
             int targetY = gridY;
             
             switch(dir) {
                 case UP: targetY--; break;
                 case RIGHT: targetX++; break;
                 case DOWN: targetY++; break;
                 case LEFT: targetX--; break;
             }
             
             // Si hay bloque, romperlo
             if (board.hasBlock(targetX, targetY)) {
                 this.direction = dir;
                 breakBlock(dir);
                 stuckCounter = 0; // Resetear
                 return; // Salir después de romper
             }
         }
         
         // Si no hay bloques para romper, intentar disparar en dirección aleatoria
         if (random.nextInt(100) < 50) {
             this.direction = directions[random.nextInt(4)];
             shootIce();
         }
     }
    
    
    public boolean isTrapped() {
    	 if (board == null) return false;
         
         // Verificar si no se ha movido
         if (gridX == lastX && gridY == lastY) {
             stuckCounter++;
         } else {
             stuckCounter = 0;
             lastX = gridX;
             lastY = gridY;
         }
         
         // Si está atascado por 3 turnos consecutivos
         if (stuckCounter >= 3) {
             return true;
         }
         
         // Verificar direcciones bloqueadas
         int[] directions = {UP, RIGHT, DOWN, LEFT};
         int blockedCount = 0;
         
         for (int dir : directions) {
             int testX = gridX;
             int testY = gridY;
             
             switch(dir) {
                 case UP: testY--; break;
                 case RIGHT: testX++; break;
                 case DOWN: testY++; break;
                 case LEFT: testX--; break;
             }
             
             if (!board.canMoveTo(testX, testY) || board.hasBlock(testX, testY)) {
                 blockedCount++;
             }
         }
         
         return blockedCount >= 3;
     }
    
    
    
    protected void moveTowards(int targetX, int targetY) {
    	
    	if (board == null) {
            moveRandomly();
            return;
        }
    	 
         
         attemptCounter++;
         
         // Si está atascado, romper bloques primero
         if (isTrapped()) {
             attemptBreakIce();
             return;
         }
         
         int dx = targetX - gridX;
         int dy = targetY - gridY;
         
         // Intentar moverse hacia el objetivo
         if (Math.abs(dx) > Math.abs(dy)) {
             // Priorizar horizontal
             if (dx > 0 && tryMove(RIGHT)) return;
             if (dx < 0 && tryMove(LEFT)) return;
             if (dy > 0 && tryMove(DOWN)) return;
             if (dy < 0 && tryMove(UP)) return;
         } else {
             // Priorizar vertical
             if (dy > 0 && tryMove(DOWN)) return;
             if (dy < 0 && tryMove(UP)) return;
             if (dx > 0 && tryMove(RIGHT)) return;
             if (dx < 0 && tryMove(LEFT)) return;
         }
         
         // Si después de varios intentos no se mueve, forzar romper bloques
         if (attemptCounter > 5) {
             attemptBreakIce();
             attemptCounter = 0;
         } else {
             moveRandomly();
         }
    }
    
    protected void moveAwayFrom(int sourceX, int sourceY) {
        int dx = gridX - sourceX;
        int dy = gridY - sourceY;
        
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0 && tryMove(RIGHT)) return;
            if (dx < 0 && tryMove(LEFT)) return;
        } else {
            if (dy > 0 && tryMove(DOWN)) return;
            if (dy < 0 && tryMove(UP)) return;
        }
        
        moveRandomly();
    }
    
    protected void moveRandomly() {
    	 int[] directions = {UP, RIGHT, DOWN, LEFT};
         
         // Mezclar direcciones aleatoriamente
         for (int i = 0; i < 4; i++) {
             int j = random.nextInt(4);
             int temp = directions[i];
             directions[i] = directions[j];
             directions[j] = temp;
         }
         
         for (int dir : directions) {
             if (tryMove(dir)) 
            	 attemptCounter = 0;
            	 return;
         }
         
         // Si no puede moverse en ninguna dirección, está atrapado
         stuckCounter++;
    }
    
    protected boolean tryMove(int direction) {
int newX = gridX, newY = gridY;
        
        switch(direction) {
            case UP: newY--; break;
            case RIGHT: newX++; break;
            case DOWN: newY++; break;
            case LEFT: newX--; break;
        }
        
        // Verificar si hay bloque
        if (board.hasBlock(newX, newY)) {
            // Si hay bloque, romperlo ocasionalmente
            if (random.nextInt(100) < 30) {
                this.direction = direction;
                breakBlock(direction);
            }
            return false; // No se movió, pero intentó romper
        }
        
        // Verificar si es seguro y puede moverse
        if (board.canMoveTo(newX, newY) && isPositionSafe(newX, newY)) {
            move(direction);
            return true;
        }
        
        return false;
    }
    
    // Método para que las IAs puedan disparar
    public void shootIfPossible() {
        // 10% de probabilidad de disparar en cada decisión
        if (random.nextInt(100) < 15) {
            shootIce();
        }
    }
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}