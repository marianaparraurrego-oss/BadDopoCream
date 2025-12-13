package exceptions;

public class BadDopoCreamExceptions extends Exception{
	public static final String State_INVALID = "Estado de juego invalido";
    public static final String TIME_EXPIRED = "Se acabo el tiempo";
    public static final String PLAYER_DEATH = "Jugador eliminado, volviendo al nivel 1";
    public static final String GAME_ERROR = "No se pudo guardar la partida";
    public static final String LOADGAME_ERROR = "No se pudo cargar la partida";
    public static final String DUPLICATE_ICECREAM = "Escoja sabores de helado diferentes";
    public static final String DUPLICATE_FRUIT = "Escoja diferentes frutas";
    
    public BadDopoCreamExceptions(String message){
        super(message);
    }

}
