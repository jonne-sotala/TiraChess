package tirachess.domain;

import tirachess.datastructures.MyArrayList;

/**
 * Class that implements the interface Player. It represents the chess AI in a chess game. 
 */
public class Bot implements Player {

    private Evaluator evaluator = new Evaluator();
    private int depth = 3;
    
    /**
     * A method that takes a position and then returns a new Position where a move
     * has been played by the bot. The bot evaluates the position and tries to give 
     * the best move for itself.  
     * 
     * @param p The current position in a game. 
     * @return Position The next position after the chosen move. 
     */
    public Position play(Position p) {
        MyArrayList<Position> moves = p.getMoves();
        Position bestMove = moves.get(0);
        Double bestEval = evaluator.alphabeta(bestMove, depth, Double.NEGATIVE_INFINITY, 
                                                        Double.POSITIVE_INFINITY);
        for (int i = 0; i < moves.size(); i++) {
            Position move = moves.get(i);
            Double moveEval = evaluator.alphabeta(move, depth, Double.NEGATIVE_INFINITY, 
                                                        Double.POSITIVE_INFINITY);
            if (p.whitesMove && moveEval > bestEval) {
                bestMove = move;
                bestEval = moveEval;
            } else if (!p.whitesMove && moveEval < bestEval) {
                bestMove = move;
                bestEval = moveEval;
            }
        }
        System.out.println("Evaluation: " + bestEval / 100);
        return bestMove;
    }
}
