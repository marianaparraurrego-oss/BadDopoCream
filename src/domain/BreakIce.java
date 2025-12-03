package domain;


public interface BreakIce {
	/**
	 * Rompe un bloque en la direccion especificada
	 * @param direction (0 = arriba, 1 = derecha, 2 = abajo, 3= izquierda)
	 * @return true si el bloque fue roto exitosamente
	 */
	
	boolean breakBlock(int direction);
	
	/**
	 * Verifica si puede romper bloques
	 * @return true si puede romper
	 */
	boolean canBreakBlocks();
	/**
	 * Obtiene el numero de bloques
	 * @return numero de bloques que puede romper en linea
	 */
	int getBreakBlocksLine();
}
