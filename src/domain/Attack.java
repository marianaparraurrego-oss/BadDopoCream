package domain;
public interface Attack {
	/**
	 * Metodo para atacar el helado
	 * @return true si el ataque fue exitoso
	 */
	boolean attack(IceCream target);
	
	/**
	 * obtiene el rango de ataque del enemigo
	 * @return rango en celdas de distancia
	 */
	int getAttackRange();
	
	/**
	 * Verifica si el enemigo puede atacar
	 * @return true si esta listo para atacar
	 */
	
	boolean canAttack();
}
