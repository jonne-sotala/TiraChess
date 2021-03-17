package tirachess.domain;

/**
 * Interface that's the base for players in a chess game. 
 */
public interface Player {

    /**
     * A method that takes a position and then returns a new Position where a move
     * has been played. 
     * 
     * @param p The current position in a game. 
     * @return Position The next position after the chosen move. 
     */
    public Position play(Position p);
    
}
