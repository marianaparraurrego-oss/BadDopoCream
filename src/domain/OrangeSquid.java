package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Representa un calamar naranja que persigue al jugador
 * y rompe bloques de hielo uno a la vez cuando los encuentra
 */
public class OrangeSquid extends Enemy implements BreakIce, Serializable {
	private static final long serialVersionUID = 1L;
    private boolean isBreakingBlock;
    private long breakStartTime;
    private static final long BREAK_DURATION = 500; // 0.5 segundos para romper un bloque
    
    /**
     * Crea un calamar naranja
     * @param gridX posición x
     * @param gridY posición y
     */
    public OrangeSquid(int gridX, int gridY) {
        super(gridX, gridY, new Color(255, 140, 0)); // Color naranja
        this.isBreakingBlock = false;
    }

    @Override
    public void walk(IceCream iceCream) {
        if (iceCream == null || board == null) return;
        
        // Si está rompiendo un bloque, esperar
        if (isBreakingBlock) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - breakStartTime >= BREAK_DURATION) {
                isBreakingBlock = false;
            } else {
                return; // Sigue rompiendo, no se mueve
            }
        }
        
        int targetX = iceCream.getGridX();
        int targetY = iceCream.getGridY();
        
        int dx = targetX - gridX;
        int dy = targetY - gridY;
        
        // Decide si moverse horizontal o verticalmente
        int newX = gridX;
        int newY = gridY;
        
        if (Math.abs(dx) > Math.abs(dy)) {
            // Moverse horizontalmente
            if (dx > 0) {
                newX = gridX + 1;
                direction = RIGHT; // Derecha
            } else if (dx < 0) {
                newX = gridX - 1;
                direction = LEFT; // Izquierda
            }
        } else {
            // Moverse verticalmente
            if (dy > 0) {
                newY = gridY + 1;
                direction = DOWN; // Abajo
            } else if (dy < 0) {
                newY = gridY - 1;
                direction = UP; // Arriba
            }
        }
        
        // Si hay un bloque, romperlo
        if (board.hasBlock(newX, newY)) {
            board.removeBlock(newX, newY);
            isBreakingBlock = true;
            breakStartTime = System.currentTimeMillis();
            return;
        }
        
        // Moverse si es posible
        if (board.canMoveTo(newX, newY)) {
            gridX = newX;
            gridY = newY;
        } else {
            // Si está bloqueado, intentar moverse en la otra dirección
            if (Math.abs(dx) > Math.abs(dy)) {
                // Intentar vertical
                if (dy > 0) {
                    newY = gridY + 1;
                } else if (dy < 0) {
                    newY = gridY - 1;
                }
                newX = gridX;
            } else {
                // Intentar horizontal
                if (dx > 0) {
                    newX = gridX + 1;
                } else if (dx < 0) {
                    newX = gridX - 1;
                }
                newY = gridY;
            }
            
            // Verificar si hay bloque en la nueva dirección
            if (board.hasBlock(newX, newY)) {
                board.removeBlock(newX, newY);
                isBreakingBlock = true;
                breakStartTime = System.currentTimeMillis();
            } else if (board.canMoveTo(newX, newY)) {
                gridX = newX;
                gridY = newY;
            }
        }
    }

    @Override
    public String getType() {
        return "OrangeSquid";
    }

    @Override
    public boolean breakBlock(int direction) {
        return true; // Puede romper bloques
    }

    @Override
    public boolean canBreakBlocks() {
        return true;
    }

    @Override
    public int getBreakBlocksLine() {
        return 1; // Solo rompe un bloque a la vez
    }

    @Override
    public int getAttackRange() {
        return 0; // Ataca solo por contacto
    }

    @Override
    public boolean canAttack() {
        return !isBreakingBlock; // No ataca mientras rompe bloques
    }
    
    /**
     * @return true si está rompiendo un bloque
     */
    public boolean isBreakingBlock() {
        return isBreakingBlock;
    }
    
    public void setDirection(int dir) {
        this.direction = dir;
    }
}