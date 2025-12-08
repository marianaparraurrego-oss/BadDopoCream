package domain;

import java.awt.Color;

/**
 * Representa un cactus que cada 30 segundos le crecen púas
 */
public class Cactus extends Fruit {
	private boolean hasSpikes;
	private long lastSpikeChange;
	private int spikeInterval = 30000; // 30 segundos
	public static final int cactusPoints = 250;
	/**
	 * Crea un cactus
	 * @param gridX
	 * @param gridY
	 */
	public Cactus(int gridX, int gridY) {
		super(gridX, gridY, cactusPoints);
		this.color = new Color(34, 139, 34);
		this.hasSpikes = false;
		this.lastSpikeChange = System.currentTimeMillis();
	}

	@Override
	public String getType() {
		return "Cactus";
	}

	@Override
	public void move() {
		 long currentTime = System.currentTimeMillis();
	        long timeSinceLastChange = currentTime - lastSpikeChange;
	        
	        if (timeSinceLastChange >= spikeInterval) {
	            hasSpikes = !hasSpikes; // Alterna entre púas y normal
	            lastSpikeChange = currentTime;
	            
	            // Cambia de color cuando tiene púas
	            if (hasSpikes) {
	                this.color = new Color(178, 34, 34); // Rojo oscuro con púas
	            } else {
	                this.color = new Color(34, 139, 34); // Verde normal
	            }
	        }
	}
	
	/**
	 * Verifica si el cactus tiene púas
	 * @return true si tiene púas
	 */
	public boolean hasSpikes() {
		return hasSpikes;
	}
	
	/**
	 * Verifica si puede ser recolectado
	 * @return true si no tiene púas
	 */
	public boolean canBeCollected() {
		return !hasSpikes;
	}
	
	
}